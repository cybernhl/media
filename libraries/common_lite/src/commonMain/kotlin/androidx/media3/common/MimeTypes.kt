package androidx.media3.common

/**
 * 媒體類型定義，對齊 androidx.media3.common.MimeTypes。
 */
object MimeTypes {
    const val VIDEO_UNKNOWN = "video/x-unknown"
    const val VIDEO_H264 = "video/avc"
    const val VIDEO_H265 = "video/hevc"
    const val VIDEO_VP9 = "video/x-vnd.on2.vp9"
    
    const val AUDIO_UNKNOWN = "audio/x-unknown"
    const val AUDIO_AAC = "audio/mp4a-latm"
    const val AUDIO_MPEG = "audio/mpeg"
    const val AUDIO_RAW = "audio/raw"
    const val AUDIO_OPUS = "audio/opus"

    fun isVideo(mimeType: String?): Boolean = mimeType?.startsWith("video/") == true
    fun isAudio(mimeType: String?): Boolean = mimeType?.startsWith("audio/") == true
}
