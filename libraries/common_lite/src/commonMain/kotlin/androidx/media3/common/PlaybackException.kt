package androidx.media3.common

/**
 * 播放異常類別 (精簡自 Media3 PlaybackException.java)
 */
open class PlaybackException(
    message: String?,
    cause: Throwable?,
    val errorCode: Int
) : Exception(message, cause) {

    companion object {
        const val ERROR_CODE_UNSPECIFIED = 1000
        const val ERROR_CODE_REMOTE_ERROR = 1001
        const val ERROR_CODE_BEHIND_LIVE_WINDOW = 1002
        const val ERROR_CODE_TIMEOUT = 1003
        
        const val ERROR_CODE_IO_UNSPECIFIED = 2000
        const val ERROR_CODE_IO_NETWORK_CONNECTION_FAILED = 2001
        const val ERROR_CODE_IO_FILE_NOT_FOUND = 2005
        
        const val ERROR_CODE_DECODER_INIT_FAILED = 4001
        const val ERROR_CODE_DECODING_FAILED = 4003
    }
}
