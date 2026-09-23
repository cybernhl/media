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

    companion object {
        @JvmField
        public val EMPTY: MediaItem = MediaItem(mediaId = "")

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
