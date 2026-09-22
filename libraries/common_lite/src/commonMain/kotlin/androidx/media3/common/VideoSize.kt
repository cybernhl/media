package androidx.media3.common

/**
 * 影片尺寸資訊 (精簡自 Media3 VideoSize.java)
 */
data class VideoSize(
    val width: Int = 0,
    val height: Int = 0,
    val pixelWidthHeightRatio: Float = 1f
) {
    companion object {
        val UNKNOWN = VideoSize(0, 0)
    }
}
