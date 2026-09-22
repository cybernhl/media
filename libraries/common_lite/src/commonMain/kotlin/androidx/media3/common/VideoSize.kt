/*
 * Copyright 2021 The Android Open Source Project
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
 * Represents the video size in Pure Kotlin KMP.
 * Aligned with androidx.media3.common.VideoSize.
 */
public data class VideoSize(
    public val width: Int = 0,
    public val height: Int = 0,
    public val pixelWidthHeightRatio: Float = 1f,
    @Deprecated("Rotation is handled internally by player; always zero.")
    public val unappliedRotationDegrees: Int = 0
) {

    @UnstableApi
    public constructor(width: Int, height: Int) : this(width, height, 1f, 0)

    @UnstableApi
    public constructor(width: Int, height: Int, pixelWidthHeightRatio: Float) : this(width, height, pixelWidthHeightRatio, 0)

    companion object {
        public val UNKNOWN: VideoSize = VideoSize(0, 0)
    }
}
