/*
 * Copyright (C) 2020 The Android Open Source Project
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
import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic

/**
 * Representation of a media item in Pure Kotlin KMP.
 * Aligned with androidx.media3.common.MediaItem.
 */
public data class MediaItem(
    @JvmField public val mediaId: String,
    @JvmField public val localConfiguration: LocalConfiguration? = null,
    @JvmField public val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY
) {
    public data class LocalConfiguration(
        @JvmField public val uri: String,
        @JvmField public val mimeType: String? = null
    )

    public data class LiveConfiguration(
        @JvmField public val targetOffsetMs: Long,
        @JvmField public val minOffsetMs: Long,
        @JvmField public val maxOffsetMs: Long,
        @JvmField public val minPlaybackSpeed: Float,
        @JvmField public val maxPlaybackSpeed: Float
    ) {
        companion object {
            @JvmField
            public val UNSET: LiveConfiguration = LiveConfiguration(C.TIME_UNSET, C.TIME_UNSET, C.TIME_UNSET, C.RATE_UNSET, C.RATE_UNSET)
        }
    }

    public data class DrmConfiguration(
        @JvmField public val schemeUuid: String,
        @JvmField public val licenseUri: String?
    )

    public data class AdsConfiguration(
        @JvmField public val adTagUri: String,
        @JvmField public val transferRect: Any? = null
    ) {
        public class Builder(adTagUri: String) {
            private var adTagUri: String = adTagUri
            public fun build(): AdsConfiguration = AdsConfiguration(adTagUri)
        }
    }

    public data class ClippingConfiguration(
        @JvmField public val startPositionMs: Long = 0,
        @JvmField public val endPositionMs: Long = C.TIME_END_OF_SOURCE,
        @JvmField public val relativeToLiveWindow: Boolean = false,
        @JvmField public val relativeToDefaultPosition: Boolean = false,
        @JvmField public val startsAtKeyFrame: Boolean = false
    ) {
        public class Builder {
            private var startPositionMs: Long = 0
            private var endPositionMs: Long = C.TIME_END_OF_SOURCE
            private var relativeToLiveWindow: Boolean = false
            private var relativeToDefaultPosition: Boolean = false
            private var startsAtKeyFrame: Boolean = false

            public fun setStartPositionMs(startPositionMs: Long) = apply { this.startPositionMs = startPositionMs }
            public fun setEndPositionMs(endPositionMs: Long) = apply { this.endPositionMs = endPositionMs }
            public fun build() = ClippingConfiguration(startPositionMs, endPositionMs, relativeToLiveWindow, relativeToDefaultPosition, startsAtKeyFrame)
        }
    }

    public data class SubtitleConfiguration(
        @JvmField public val uri: String,
        @JvmField public val mimeType: String?,
        @JvmField public val language: String? = null,
        @JvmField public val selectionFlags: Int = 0,
        @JvmField public val roleFlags: Int = 0,
        @JvmField public val label: String? = null
    )

    companion object {
        @JvmField
        public val EMPTY: MediaItem = MediaItem(mediaId = "")

        @JvmStatic
        public fun fromUri(uri: String): MediaItem {
            return MediaItem(
                mediaId = uri,
                localConfiguration = LocalConfiguration(uri = uri)
            )
        }
    }

    public class Builder {
        private var mediaId: String? = null
        private var uri: String? = null
        private var mediaMetadata: MediaMetadata = MediaMetadata.EMPTY

        public fun setMediaId(mediaId: String): Builder = apply { this.mediaId = mediaId }
        public fun setUri(uri: String?): Builder = apply { this.uri = uri }
        public fun setMediaMetadata(mediaMetadata: MediaMetadata): Builder = apply { this.mediaMetadata = mediaMetadata }

        public fun build(): MediaItem {
            val finalUri = uri ?: ""
            return MediaItem(
                mediaId = mediaId ?: finalUri,
                localConfiguration = LocalConfiguration(uri = finalUri),
                mediaMetadata = mediaMetadata
            )
        }
    }
}
