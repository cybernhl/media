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

import kotlin.jvm.JvmStatic

object MimeTypes {
    public const val VIDEO_UNKNOWN: String = "video/x-unknown"
    public const val VIDEO_H264: String = "video/avc"
    public const val VIDEO_H265: String = "video/hevc"
    public const val VIDEO_VP9: String = "video/x-vnd.on2.vp9"

    public const val AUDIO_UNKNOWN: String = "audio/x-unknown"
    public const val AUDIO_AAC: String = "audio/mp4a-latm"
    public const val AUDIO_MPEG: String = "audio/mpeg"
    public const val AUDIO_RAW: String = "audio/raw"
    public const val AUDIO_OPUS: String = "audio/opus"

    @JvmStatic
    public fun isVideo(mimeType: String?): Boolean = mimeType?.startsWith("video/") == true

    @JvmStatic
    public fun isAudio(mimeType: String?): Boolean = mimeType?.startsWith("audio/") == true
}
