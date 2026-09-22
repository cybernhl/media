package androidx.media3.common

import androidx.media3.common.util.UnstableApi

/**
 * 視圖提供者介面，對齊 androidx.media3.common.ViewProvider。
 * 用於在不依賴具體 UI 框架的情況下取得播放器視圖。
 */
@UnstableApi
interface ViewProvider {
    /**
     * 回傳包含視圖的物件。
     * @param container 容器 (Android 為 ViewGroup, JVM 則視渲染實作而定)
     * @return 視圖物件 (例如 Android 的 ListenableFuture<View> 或 JVM 的 Node/Component)
     */
    fun getView(container: Any?): Any?
}
