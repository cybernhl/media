package androidx.media3.common

/**
 * 核心常量定義，完全對齊 androidx.media3.common.C。
 */
object C {
    const val INDEX_UNSET = -1
    const val TIME_UNSET = Long.MIN_VALUE + 1
    const val TIME_END_OF_SOURCE = Long.MIN_VALUE

    // Track types
    const val TRACK_TYPE_UNKNOWN = -1
    const val TRACK_TYPE_DEFAULT = 0
    const val TRACK_TYPE_AUDIO = 1
    const val TRACK_TYPE_VIDEO = 2
    const val TRACK_TYPE_TEXT = 3
    const val TRACK_TYPE_IMAGE = 6

    // PCM Encodings
    const val ENCODING_INVALID = 0
    const val ENCODING_PCM_16BIT = 2
    const val ENCODING_PCM_24BIT = 268435456
    const val ENCODING_PCM_32BIT = 536870912
    const val ENCODING_PCM_FLOAT = 4

    // Selection flags
    const val SELECTION_FLAG_DEFAULT = 1
    const val SELECTION_FLAG_FORCED = 2

    // Role flags
    const val ROLE_FLAG_MAIN = 1
    const val ROLE_FLAG_ALTERNATE = 2

    // Audio attributes
    const val USAGE_MEDIA = 1
    const val USAGE_ALARM = 4
    const val USAGE_NOTIFICATION = 5
    const val USAGE_ASSISTANCE_SONIFICATION = 13
    const val USAGE_GAME = 14

    const val CONTENT_TYPE_UNKNOWN = 0
    const val CONTENT_TYPE_SPEECH = 1
    const val CONTENT_TYPE_MUSIC = 2
    const val CONTENT_TYPE_MOVIE = 3
    const val CONTENT_TYPE_SONIFICATION = 4

    const val AUDIO_FLAGS_NONE = 0

    // Video constants
    const val COLOR_SPACE_BT709 = 1
    const val COLOR_RANGE_LIMITED = 2
    const val COLOR_TRANSFER_SDR = 3

    // Buffer flags
    const val BUFFER_FLAG_KEY_FRAME = 1
    const val BUFFER_FLAG_END_OF_STREAM = 4
    const val BUFFER_FLAG_DECODE_ONLY = 2147483648.toInt() // 0x80000000

    // Video output modes
    const val VIDEO_OUTPUT_MODE_NONE = -1
    const val VIDEO_OUTPUT_MODE_YUV = 0
    const val VIDEO_OUTPUT_MODE_SURFACE_YUV = 1
}
