package androidx.media3.common

/**
 * 播放參數，主要用於控制播放速度。
 */
data class PlaybackParameters(
    val speed: Float = 1.0f,
    val pitch: Float = 1.0f
) {
    companion object {
        val DEFAULT = PlaybackParameters(1.0f, 1.0f)
    }
}
