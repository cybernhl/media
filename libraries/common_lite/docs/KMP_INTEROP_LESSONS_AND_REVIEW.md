# Media3 `common_lite` (KMP) Java-Kotlin 互通性與架構檢討報告

本文件記錄了將 AndroidX Media3 核心模組從 Java 遷移至 Kotlin Multiplatform (KMP `common_lite`) 過程中，遭遇的關鍵技術挑戰、架構陷阱、以及為確保 **Java-Kotlin 二進位/源碼相容性** 所採取的修正策略。

---

## 一、 核心痛點與檢討項目

### 1. Java 屬性與 Kotlin 屬性編譯差異 (`@JvmField` 的必要性)

* **問題描述**：
  在原始 Android Media3 Java 實作中（如 `PlaybackParameters.java`、`VideoSize.java`、`AudioAttributes.java` 等），許多資料模型的屬性是直接宣告為 **`public final` 的公開欄位**（例如 `public final float speed;`）。
  當初改寫為 Kotlin 時，若寫成 `public val speed: Float`，Kotlin 編譯器會将其轉譯為 **`private` 欄位 + `getSpeed()` Getter 方法**。
  這會導致兩個嚴重問題：
  1. **Java 呼叫端編譯失敗**：既有 Java 程式碼若直接存取 `params.speed` 或 `videoSize.width`，會因找不到公開欄位而報錯。
  2. **Companion Object 常數呼叫改變**：如 `PlaybackParameters.DEFAULT` 在未加 `@JvmField` 時，Java 端必須寫成 `PlaybackParameters.Companion.getDEFAULT()`。

* **修正策略**：
  全面為所有對應到 Java 公開欄位的屬性以及 Companion Object 中的靜態實例/常數（`DEFAULT`、`UNKNOWN`、`EMPTY`、`SDR_BT709` 等）加上 **`@JvmField`** 標註：
  ```kotlin
  public data class PlaybackParameters(
      @JvmField public val speed: Float = 1.0f,
      @JvmField public val pitch: Float = 1.0f
  ) {
      companion object {
          @JvmField
          public val DEFAULT: PlaybackParameters = PlaybackParameters(1.0f, 1.0f)
      }
  }
  ```

---

### 2. 位元組碼汙染與頂層私有類別 (`Private Helper Classes`)

* **問題描述**：
  在實作 `PlayerExtensions.kt` 的協程監聽時，初期曾將監聽器抽離為檔案頂層的私有輔助類別（`private class PlayerListener`）。
  這會導致 Kotlin 編譯器在產生 `.class` 檔案時，額外生成帶有 `*Kt$` 雜湊或內部巢狀的類別檔案，造成套件與位元組碼結構雜亂。

* **修正策略**：
  遵循 Kotlin 最佳實踐，改用區域匿名物件 (`object : Player.Listener`) 直接封裝在 `suspendCancellableCoroutine` 內部，消除了額外的頂層類別汙染與平台命名衝突。

---

### 3. 官方 API 二進位簽名對齊 (`Binary Signature Alignment`)

* **問題描述**：
  為了讓 `common_lite` 能夠無縫替代或支援原有的 KTX 擴充功能，高階函式（如 `listenTo`）的參數結構必須與官方 `media3-common-ktx` 的 JVM 位元組碼簽名保持一致，否則下游模組在連結時會發生 `NoSuchMethodError` 或簽名不符。

* **修正策略**：
  對齊官方 `common_ktx` 的 `listenTo` 簽名：
  ```kotlin
  suspend fun Player.listenTo(
      @Player.Event firstEvent: Int,
      vararg otherEvents: @Player.Event Int,
      onEvents: Player.(Player.Events) -> Unit,
  ): Nothing {
      val eventArray = intArrayOf(firstEvent, *otherEvents)
      listenImpl(eventArray, onEvents)
  }
  ```

---

### 4. KMP 平台的執行緒與生命週期適配 (`PlayerExtensions.kt`)

* **問題描述**：
  原始 `common_ktx` 強度依賴 Android 專屬的 `android.os.Looper` 與 `androidx.core.os.HandlerCompat` 來確保回呼執行緒。但在 KMP (`commonMain`) 中無法直接呼叫 Android 框架 API。

* **修正策略**：
  改用跨平台的 `kotlinx.coroutines.suspendCancellableCoroutine`，並嚴格實作：
  * **異常傳遞**：當 `onEvents` 發生例外時，透過 `continuation.resumeWithException(t)` 終止協程並向上拋出。
  * **資源清理**：透過 `continuation.invokeOnCancellation { removeListener(listener) }` 確保協程取消時自動移除監聽器，避免記憶體流失。

---

## 二、 遷移至 CMP (JetBrains Compose) 時的編譯器雜湊與 `NoSuchMethodError` 陷阱

在將 UI 模組或元件改寫為 CMP (JetBrains Compose Multiplatform) 時（例如 `PreviousButton` 等帶有大量 `@Composable` 預設參數與多載的元件），常會遇到類似以下錯誤：
`java.lang.NoSuchMethodError: No static method PreviousButton-FHprtrg(...)`

### 1. 成因分析
* **預設參數雜湊混淆 (Default Argument Mangling)**：
  當 `@Composable` 函式具有多個預設引數（如預設圖示, 顏色, 點擊行為等）時，Kotlin 編譯器會在底層生成帶有雜湊後綴（如 `-FHprtrg`）的合成靜態方法，用以處理預設參數的傳遞。
* **增量編譯快取不一致 (Stale Incremental Build Cache)**：
  當函式庫（如 `ui_compose_material3`）中的元件簽名或預設參數被重構後，如果未完全清理快取，呼叫端模組會繼續尋找舊版的雜湊方法名稱（例如舊的 `-FHprtrg`），導致執行期發生 `NoSuchMethodError`。
* **多載與 `@JvmName` 衝突**：
  同時提供多個同名且帶有預設參數的多載函式（例如同時支援 `Painter` 與 `ImageVector` 的 `PreviousButton`），會使編譯器產生大量複雜的雜湊靜態方法，大幅增加 ABI 不一致的風險。

### 2. 預防與解決方案
1. **強制執行 Clean Build**：
   每次對跨平台 UI 模組進行重構後，必須徹底清除快取：
   ```powershell
   ./gradlew clean
   ./gradlew build --refresh-dependencies
   ```
2. **精簡預設參數**：
   避免在複雜的跨平台 Compose 元件中堆疊過多的 `@Composable` Lambda 預設參數，必要時改用明確的多載或 Builder 模式，減少編譯器產生的 mangled 靜態方法複雜度。

---

## 三、 總結與檢驗結果

經過上述全面檢討與修正：
1. `common_lite` 的資料模型類別均已具備完整的 **Java 互通性 (`@JvmField`)**。
2. 擴充函式已具備 **KMP 跨平台相容性** 與 **官方二進位簽名對齊**。
3. 識別並記錄了 CMP 遷移過程中的 **編譯器預設參數雜湊 (`NoSuchMethodError`) 陷阱與解法**。
4. 經 Gradle 驗證，`:lib-common-lite:assemble` 可**零錯誤, 零警告**順利建置完成。
