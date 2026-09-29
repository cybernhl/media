package androidx.media3.common

import kotlin.jvm.JvmField

/**
 * 播放異常類別 (精簡自 Media3 PlaybackException.java)
 */
open class PlaybackException(
    message: String?,
    cause: Throwable?,
    @JvmField val errorCode: Int
) : Exception(message, cause) {

    @Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
    annotation class ErrorCode

    companion object {
        const val ERROR_CODE_INVALID_STATE = -1000
        const val ERROR_CODE_BAD_VALUE = -1001
        const val ERROR_CODE_PERMISSION_DENIED = -1002
        const val ERROR_CODE_NOT_SUPPORTED = -1003
        const val ERROR_CODE_DISCONNECTED = -1004
        const val ERROR_CODE_AUTHENTICATION_EXPIRED = -1005
        const val ERROR_CODE_PREMIUM_ACCOUNT_REQUIRED = -1006
        const val ERROR_CODE_CONCURRENT_STREAM_LIMIT = -1007
        const val ERROR_CODE_PARENTAL_CONTROL_RESTRICTED = -1008
        const val ERROR_CODE_NOT_AVAILABLE_IN_REGION = -1009
        const val ERROR_CODE_SKIP_LIMIT_REACHED = -1010
        const val ERROR_CODE_SETUP_REQUIRED = -1011
        const val ERROR_CODE_END_OF_PLAYLIST = -1012
        const val ERROR_CODE_CONTENT_ALREADY_PLAYING = -1013

        const val ERROR_CODE_UNSPECIFIED = 1000
        const val ERROR_CODE_REMOTE_ERROR = 1001
        const val ERROR_CODE_BEHIND_LIVE_WINDOW = 1002
        const val ERROR_CODE_TIMEOUT = 1003
        const val ERROR_CODE_FAILED_RUNTIME_CHECK = 1004

        const val ERROR_CODE_IO_UNSPECIFIED = 2000
        const val ERROR_CODE_IO_NETWORK_CONNECTION_FAILED = 2001
        const val ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT = 2002
        const val ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE = 2003
        const val ERROR_CODE_IO_BAD_HTTP_STATUS = 2004
        const val ERROR_CODE_IO_FILE_NOT_FOUND = 2005
        const val ERROR_CODE_IO_NO_PERMISSION = 2006
        const val ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED = 2007
        const val ERROR_CODE_IO_READ_POSITION_OUT_OF_RANGE = 2008

        const val ERROR_CODE_PARSING_CONTAINER_MALFORMED = 3001
        const val ERROR_CODE_PARSING_MANIFEST_MALFORMED = 3002
        const val ERROR_CODE_PARSING_CONTAINER_UNSUPPORTED = 3003
        const val ERROR_CODE_PARSING_MANIFEST_UNSUPPORTED = 3004

        const val ERROR_CODE_DECODER_INIT_FAILED = 4001
        const val ERROR_CODE_DECODER_QUERY_FAILED = 4002
        const val ERROR_CODE_DECODING_FAILED = 4003
        const val ERROR_CODE_DECODING_FORMAT_EXCEEDS_CAPABILITIES = 4004
        const val ERROR_CODE_DECODING_FORMAT_UNSUPPORTED = 4005
        const val ERROR_CODE_DECODING_RESOURCES_RECLAIMED = 4006

        const val ERROR_CODE_AUDIO_TRACK_INIT_FAILED = 5001
        const val ERROR_CODE_AUDIO_TRACK_WRITE_FAILED = 5002
        const val ERROR_CODE_AUDIO_TRACK_OFFLOAD_WRITE_FAILED = 5003
        const val ERROR_CODE_AUDIO_TRACK_OFFLOAD_INIT_FAILED = 5004

        const val ERROR_CODE_DRM_UNSPECIFIED = 6000
        const val ERROR_CODE_DRM_SCHEME_UNSUPPORTED = 6001
        const val ERROR_CODE_DRM_PROVISIONING_FAILED = 6002
        const val ERROR_CODE_DRM_CONTENT_ERROR = 6003
        const val ERROR_CODE_DRM_LICENSE_ACQUISITION_FAILED = 6004
        const val ERROR_CODE_DRM_DISALLOWED_OPERATION = 6005
        const val ERROR_CODE_DRM_SYSTEM_ERROR = 6006
        const val ERROR_CODE_DRM_DEVICE_REVOKED = 6007
        const val ERROR_CODE_DRM_LICENSE_EXPIRED = 6008

        const val ERROR_CODE_VIDEO_FRAME_PROCESSOR_INIT_FAILED = 7001
        const val ERROR_CODE_VIDEO_FRAME_PROCESSING_FAILED = 7002
    }
}
