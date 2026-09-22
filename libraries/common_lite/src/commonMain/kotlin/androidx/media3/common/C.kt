/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.media3.common

import androidx.media3.common.util.UnstableApi

/**
 * Defines constants used by the library in Pure Kotlin KMP.
 * Complete and aligned with androidx.media3.common.C.
 */
public object C {

    // Unset & Time Constants
    public const val TIME_END_OF_SOURCE: Long = Long.MIN_VALUE
    public const val TIME_UNSET: Long = Long.MIN_VALUE + 1
    public const val INDEX_UNSET: Int = -1
    @Deprecated("Use INDEX_UNSET.")
    @UnstableApi
    public const val POSITION_UNSET: Int = INDEX_UNSET
    public const val RATE_UNSET: Float = -Float.MAX_VALUE
    @UnstableApi public const val RATE_UNSET_INT: Int = Int.MIN_VALUE + 1
    public const val LENGTH_UNSET: Int = -1
    @UnstableApi public const val PERCENTAGE_UNSET: Int = -1

    // Time Conversion Units
    @UnstableApi public const val MILLIS_PER_SECOND: Long = 1_000L
    @UnstableApi public const val MICROS_PER_SECOND: Long = 1_000_000L
    @UnstableApi public const val NANOS_PER_SECOND: Long = 1_000_000_000L

    // Data Size Units
    @UnstableApi public const val BITS_PER_BYTE: Int = 8
    @UnstableApi public const val BYTES_PER_FLOAT: Int = 4

    // Font Names
    @UnstableApi public const val SERIF_NAME: String = "serif"
    @UnstableApi public const val SANS_SERIF_NAME: String = "sans-serif"

    // SSAI / CSAI Schemes
    @UnstableApi public const val SSAI_SCHEME: String = "ssai"
    @UnstableApi public const val CSAI_SCHEME: String = "csai"

    // Crypto Types
    public const val CRYPTO_TYPE_NONE: Int = 0
    public const val CRYPTO_TYPE_UNSUPPORTED: Int = 1
    public const val CRYPTO_TYPE_FRAMEWORK: Int = 2
    public const val CRYPTO_TYPE_CUSTOM_BASE: Int = 10000

    // Crypto Modes
    @UnstableApi public const val CRYPTO_MODE_UNENCRYPTED: Int = 0
    @UnstableApi public const val CRYPTO_MODE_AES_CTR: Int = 1
    @UnstableApi public const val CRYPTO_MODE_AES_CBC: Int = 2

    // Audio Session ID
    @UnstableApi public const val AUDIO_SESSION_ID_UNSET: Int = 0

    // Audio Encodings
    public const val ENCODING_INVALID: Int = 0
    public const val ENCODING_PCM_8BIT: Int = 3
    public const val ENCODING_PCM_16BIT: Int = 2
    @UnstableApi public const val ENCODING_PCM_16BIT_BIG_ENDIAN: Int = 0x10000000
    public const val ENCODING_PCM_24BIT: Int = 268435456 // AudioFormat.ENCODING_PCM_24BIT_PACKED
    @UnstableApi public const val ENCODING_PCM_24BIT_BIG_ENDIAN: Int = 0x50000000
    public const val ENCODING_PCM_32BIT: Int = 536870912 // AudioFormat.ENCODING_PCM_32BIT
    @UnstableApi public const val ENCODING_PCM_32BIT_BIG_ENDIAN: Int = 0x60000000
    public const val ENCODING_PCM_FLOAT: Int = 4
    @UnstableApi public const val ENCODING_PCM_FLOAT_BIG_ENDIAN: Int = 0x71000000
    @UnstableApi public const val ENCODING_PCM_DOUBLE: Int = 0x70000000
    @UnstableApi public const val ENCODING_PCM_DOUBLE_BIG_ENDIAN: Int = 0x72000000

    public const val ENCODING_MP3: Int = 9
    public const val ENCODING_AAC_LC: Int = 10
    public const val ENCODING_AAC_HE_V1: Int = 11
    public const val ENCODING_AAC_HE_V2: Int = 12
    public const val ENCODING_AAC_XHE: Int = 16
    public const val ENCODING_AAC_ELD: Int = 15
    @UnstableApi public const val ENCODING_AAC_ER_BSAC: Int = 0x40000000

    public const val ENCODING_AC3: Int = 5
    public const val ENCODING_E_AC3: Int = 6
    public const val ENCODING_E_AC3_JOC: Int = 18
    public const val ENCODING_AC4: Int = 17
    public const val ENCODING_DTS: Int = 7
    public const val ENCODING_DTS_HD: Int = 8
    public const val ENCODING_DTS_UHD_P2: Int = 30
    public const val ENCODING_DOLBY_TRUEHD: Int = 14
    public const val ENCODING_OPUS: Int = 20
    @UnstableApi public const val ENCODING_DSD: Int = 31

    // Spatialization Behavior
    public const val SPATIALIZATION_BEHAVIOR_AUTO: Int = 0
    public const val SPATIALIZATION_BEHAVIOR_NEVER: Int = 1

    // Audio Stream Types
    public const val STREAM_TYPE_ALARM: Int = 4
    public const val STREAM_TYPE_DTMF: Int = 8
    public const val STREAM_TYPE_MUSIC: Int = 3
    public const val STREAM_TYPE_NOTIFICATION: Int = 5
    public const val STREAM_TYPE_RING: Int = 2
    public const val STREAM_TYPE_SYSTEM: Int = 1
    public const val STREAM_TYPE_VOICE_CALL: Int = 0
    public const val STREAM_TYPE_ACCESSIBILITY: Int = 10
    public const val STREAM_TYPE_DEFAULT: Int = STREAM_TYPE_MUSIC

    // Volume Flags
    public const val VOLUME_FLAG_SHOW_UI: Int = 1
    public const val VOLUME_FLAG_ALLOW_RINGER_MODES: Int = 2
    public const val VOLUME_FLAG_PLAY_SOUND: Int = 4
    public const val VOLUME_FLAG_REMOVE_SOUND_AND_VIBRATE: Int = 8
    public const val VOLUME_FLAG_VIBRATE: Int = 16

    // Volume Operation Types
    @UnstableApi public const val VOLUME_OPERATION_TYPE_SET_VOLUME: Int = 0
    @UnstableApi public const val VOLUME_OPERATION_TYPE_MUTE: Int = 1
    @UnstableApi public const val VOLUME_OPERATION_TYPE_UNMUTE: Int = 2

    // Audio Content Types
    public const val AUDIO_CONTENT_TYPE_MOVIE: Int = 3
    @UnstableApi @Deprecated("Use AUDIO_CONTENT_TYPE_MOVIE instead.")
    public const val CONTENT_TYPE_MOVIE: Int = AUDIO_CONTENT_TYPE_MOVIE

    public const val AUDIO_CONTENT_TYPE_MUSIC: Int = 2
    @UnstableApi @Deprecated("Use AUDIO_CONTENT_TYPE_MUSIC instead.")
    public const val CONTENT_TYPE_MUSIC: Int = AUDIO_CONTENT_TYPE_MUSIC

    public const val AUDIO_CONTENT_TYPE_SONIFICATION: Int = 4
    @UnstableApi @Deprecated("Use AUDIO_CONTENT_TYPE_SONIFICATION instead.")
    public const val CONTENT_TYPE_SONIFICATION: Int = AUDIO_CONTENT_TYPE_SONIFICATION

    public const val AUDIO_CONTENT_TYPE_SPEECH: Int = 1
    @UnstableApi @Deprecated("Use AUDIO_CONTENT_TYPE_SPEECH instead.")
    public const val CONTENT_TYPE_SPEECH: Int = AUDIO_CONTENT_TYPE_SPEECH

    public const val AUDIO_CONTENT_TYPE_UNKNOWN: Int = 0
    @UnstableApi @Deprecated("Use AUDIO_CONTENT_TYPE_UNKNOWN instead.")
    public const val CONTENT_TYPE_UNKNOWN: Int = AUDIO_CONTENT_TYPE_UNKNOWN

    // Audio Flags
    public const val FLAG_AUDIBILITY_ENFORCED: Int = 1
    public const val AUDIO_FLAGS_NONE: Int = 0

    // Audio Usages
    public const val USAGE_ALARM: Int = 4
    public const val USAGE_ASSISTANCE_ACCESSIBILITY: Int = 11
    public const val USAGE_ASSISTANCE_NAVIGATION_GUIDANCE: Int = 12
    public const val USAGE_ASSISTANCE_SONIFICATION: Int = 13
    public const val USAGE_ASSISTANT: Int = 16
    public const val USAGE_GAME: Int = 14
    public const val USAGE_MEDIA: Int = 1
    public const val USAGE_NOTIFICATION: Int = 5
    public const val USAGE_NOTIFICATION_COMMUNICATION_DELAYED: Int = 9
    public const val USAGE_NOTIFICATION_COMMUNICATION_INSTANT: Int = 8
    public const val USAGE_NOTIFICATION_COMMUNICATION_REQUEST: Int = 7
    public const val USAGE_NOTIFICATION_EVENT: Int = 10
    public const val USAGE_NOTIFICATION_RINGTONE: Int = 6
    public const val USAGE_UNKNOWN: Int = 0
    public const val USAGE_VOICE_COMMUNICATION: Int = 2
    public const val USAGE_VOICE_COMMUNICATION_SIGNALLING: Int = 3

    // Audio Allowed Capture Policy
    public const val ALLOW_CAPTURE_BY_ALL: Int = 1
    public const val ALLOW_CAPTURE_BY_NONE: Int = 2
    public const val ALLOW_CAPTURE_BY_SYSTEM: Int = 3

    // Video Codec Flags
    @UnstableApi public const val VIDEO_CODEC_FLAG_H264: Int = 1
    @UnstableApi public const val VIDEO_CODEC_FLAG_H265: Int = 2

    // Buffer Flags
    @UnstableApi public const val BUFFER_FLAG_KEY_FRAME: Int = 1
    @UnstableApi public const val BUFFER_FLAG_END_OF_STREAM: Int = 4
    @UnstableApi public const val BUFFER_FLAG_NOT_DEPENDED_ON: Int = 1 shl 26 // 0x04000000
    @UnstableApi public const val BUFFER_FLAG_FIRST_SAMPLE: Int = 1 shl 27     // 0x08000000
    @UnstableApi public const val BUFFER_FLAG_HAS_SUPPLEMENTAL_DATA: Int = 1 shl 28 // 0x10000000
    @UnstableApi public const val BUFFER_FLAG_LAST_SAMPLE: Int = 1 shl 29      // 0x20000000
    @UnstableApi public const val BUFFER_FLAG_ENCRYPTED: Int = 1 shl 30        // 0x40000000
    @UnstableApi public const val BUFFER_FLAG_DECODE_ONLY: Int = 2147483648.toInt() // 0x80000000

    // Codec Priority
    @UnstableApi public const val MEDIA_CODEC_PRIORITY_REALTIME: Int = 0
    @UnstableApi public const val MEDIA_CODEC_PRIORITY_NON_REALTIME: Int = 1

    // Video Output Modes
    @UnstableApi public const val VIDEO_OUTPUT_MODE_NONE: Int = -1
    @UnstableApi public const val VIDEO_OUTPUT_MODE_YUV: Int = 0
    @UnstableApi public const val VIDEO_OUTPUT_MODE_SURFACE_YUV: Int = 1

    // Video Scaling Modes
    @UnstableApi public const val VIDEO_SCALING_MODE_SCALE_TO_FIT: Int = 1
    @UnstableApi public const val VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING: Int = 2
    @UnstableApi public const val VIDEO_SCALING_MODE_DEFAULT: Int = VIDEO_SCALING_MODE_SCALE_TO_FIT

    // Video Frame Rate Change Strategies
    @UnstableApi public const val VIDEO_CHANGE_FRAME_RATE_STRATEGY_OFF: Int = Int.MIN_VALUE
    @UnstableApi public const val VIDEO_CHANGE_FRAME_RATE_STRATEGY_ONLY_IF_SEAMLESS: Int = 0

    // Track Selection Flags
    public const val SELECTION_FLAG_DEFAULT: Int = 1
    public const val SELECTION_FLAG_FORCED: Int = 1 shl 1 // 2
    public const val SELECTION_FLAG_AUTOSELECT: Int = 1 shl 2 // 4

    // Undetermined Language Code
    public const val LANGUAGE_UNDETERMINED: String = "und"

    // Content Types
    public const val CONTENT_TYPE_DASH: Int = 0
    @Deprecated("Use CONTENT_TYPE_DASH instead.")
    @UnstableApi
    public const val TYPE_DASH: Int = CONTENT_TYPE_DASH

    public const val CONTENT_TYPE_SS: Int = 1
    @Deprecated("Use CONTENT_TYPE_SS instead.")
    @UnstableApi
    public const val TYPE_SS: Int = CONTENT_TYPE_SS

    public const val CONTENT_TYPE_HLS: Int = 2
    @Deprecated("Use CONTENT_TYPE_HLS instead.")
    @UnstableApi
    public const val TYPE_HLS: Int = CONTENT_TYPE_HLS

    public const val CONTENT_TYPE_RTSP: Int = 3
    @Deprecated("Use CONTENT_TYPE_RTSP instead.")
    @UnstableApi
    public const val TYPE_RTSP: Int = CONTENT_TYPE_RTSP

    public const val CONTENT_TYPE_OTHER: Int = 4
    @Deprecated("Use CONTENT_TYPE_OTHER instead.")
    @UnstableApi
    public const val TYPE_OTHER: Int = CONTENT_TYPE_OTHER

    // Result Codes
    @UnstableApi public const val RESULT_END_OF_INPUT: Int = -1
    @UnstableApi public const val RESULT_MAX_LENGTH_EXCEEDED: Int = -2
    @UnstableApi public const val RESULT_NOTHING_READ: Int = -3
    @UnstableApi public const val RESULT_BUFFER_READ: Int = -4
    @UnstableApi public const val RESULT_FORMAT_READ: Int = -5

    // Data Types
    @UnstableApi public const val DATA_TYPE_UNKNOWN: Int = 0
    @UnstableApi public const val DATA_TYPE_MEDIA: Int = 1
    @UnstableApi public const val DATA_TYPE_MEDIA_INITIALIZATION: Int = 2
    @UnstableApi public const val DATA_TYPE_DRM: Int = 3
    @UnstableApi public const val DATA_TYPE_MANIFEST: Int = 4
    @UnstableApi public const val DATA_TYPE_TIME_SYNCHRONIZATION: Int = 5
    @UnstableApi public const val DATA_TYPE_AD: Int = 6
    @UnstableApi public const val DATA_TYPE_MEDIA_PROGRESSIVE_LIVE: Int = 7
    @UnstableApi public const val DATA_TYPE_STEERING_MANIFEST: Int = 8
    @UnstableApi public const val DATA_TYPE_CUSTOM_BASE: Int = 10000

    // Track Types
    public const val TRACK_TYPE_NONE: Int = -2
    public const val TRACK_TYPE_UNKNOWN: Int = -1
    public const val TRACK_TYPE_DEFAULT: Int = 0
    public const val TRACK_TYPE_AUDIO: Int = 1
    public const val TRACK_TYPE_VIDEO: Int = 2
    public const val TRACK_TYPE_TEXT: Int = 3
    public const val TRACK_TYPE_IMAGE: Int = 4
    public const val TRACK_TYPE_METADATA: Int = 5
    public const val TRACK_TYPE_CAMERA_MOTION: Int = 6
    public const val TRACK_TYPE_CUSTOM_BASE: Int = 10000

    // Selection Reasons
    @UnstableApi public const val SELECTION_REASON_UNKNOWN: Int = 0
    @UnstableApi public const val SELECTION_REASON_INITIAL: Int = 1
    @UnstableApi public const val SELECTION_REASON_MANUAL: Int = 2
    @UnstableApi public const val SELECTION_REASON_ADAPTIVE: Int = 3
    @UnstableApi public const val SELECTION_REASON_TRICK_PLAY: Int = 4
    @UnstableApi public const val SELECTION_REASON_CUSTOM_BASE: Int = 10000

    // Default Buffer & Increment Sizes
    @UnstableApi public const val DEFAULT_BUFFER_SEGMENT_SIZE: Int = 64 * 1024
    public const val DEFAULT_SEEK_BACK_INCREMENT_MS: Long = 5_000L
    public const val DEFAULT_SEEK_FORWARD_INCREMENT_MS: Long = 15_000L
    public const val DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS: Long = 3_000L

    // CENC Schemes
    @UnstableApi public const val CENC_TYPE_cenc: String = "cenc"
    @UnstableApi public const val CENC_TYPE_cbc1: String = "cbc1"
    @UnstableApi public const val CENC_TYPE_cens: String = "cens"
    @UnstableApi public const val CENC_TYPE_cbcs: String = "cbcs"

    // Stereo Modes
    @UnstableApi public const val STEREO_MODE_MONO: Int = 0
    @UnstableApi public const val STEREO_MODE_TOP_BOTTOM: Int = 1
    @UnstableApi public const val STEREO_MODE_LEFT_RIGHT: Int = 2
    @UnstableApi public const val STEREO_MODE_STEREO_MESH: Int = 3
    @UnstableApi public const val STEREO_MODE_INTERLEAVED_LEFT_PRIMARY: Int = 4
    @UnstableApi public const val STEREO_MODE_INTERLEAVED_RIGHT_PRIMARY: Int = 5

    // Color Spaces
    @UnstableApi public const val COLOR_SPACE_BT601: Int = 2
    @UnstableApi public const val COLOR_SPACE_BT709: Int = 1
    @UnstableApi public const val COLOR_SPACE_BT2020: Int = 6

    // Color Transfer
    @UnstableApi public const val COLOR_TRANSFER_LINEAR: Int = 1
    @UnstableApi public const val COLOR_TRANSFER_SDR: Int = 3
    @UnstableApi public const val COLOR_TRANSFER_SRGB: Int = 2
    @UnstableApi public const val COLOR_TRANSFER_GAMMA_2_2: Int = 10
    @UnstableApi public const val COLOR_TRANSFER_ST2084: Int = 6
    @UnstableApi public const val COLOR_TRANSFER_HLG: Int = 7

    // Color Range
    @UnstableApi public const val COLOR_RANGE_LIMITED: Int = 2
    @UnstableApi public const val COLOR_RANGE_FULL: Int = 1
}
