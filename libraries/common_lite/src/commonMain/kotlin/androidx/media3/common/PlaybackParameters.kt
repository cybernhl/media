/*
 * Copyright (C) 2017 The Android Open Source Project
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
import kotlin.math.roundToLong

/**
 * Parameters that apply to playback, including speed and pitch setting in Pure Kotlin KMP.
 * Aligned with androidx.media3.common.PlaybackParameters.
 */
public data class PlaybackParameters(
    public val speed: Float = 1.0f,
    public val pitch: Float = 1.0f
) {
    init {
        require(speed > 0f) { "speed must be > 0" }
        require(pitch > 0f) { "pitch must be > 0" }
    }

    private val scaledUsPerMs: Long = (speed * 1000f).roundToLong()

    @UnstableApi
    public fun getMediaTimeUsForPlayoutTimeMs(timeMs: Long): Long = timeMs * scaledUsPerMs

    public fun withSpeed(speed: Float): PlaybackParameters = copy(speed = speed)

    @UnstableApi
    public fun withPitch(pitch: Float): PlaybackParameters = copy(pitch = pitch)

    companion object {
        public val DEFAULT: PlaybackParameters = PlaybackParameters(1.0f, 1.0f)
    }
}
