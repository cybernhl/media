/*
 * Copyright 2017 The Android Open Source Project
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
 * Attributes for audio playback in Pure Kotlin KMP.
 * Complete and aligned with androidx.media3.common.AudioAttributes.
 */
public class AudioAttributes private constructor(
    public val contentType: Int,
    public val flags: Int,
    public val usage: Int,
    public val allowedCapturePolicy: Int,
    public val spatializationBehavior: Int,
    @UnstableApi public val isContentSpatialized: Boolean,
    @UnstableApi public val hapticChannelsMuted: Boolean
) {

    @UnstableApi
    public fun buildUpon(): Builder = Builder(this)

    /** Returns the volume control stream for these audio attributes. */
    public fun getVolumeControlStream(): Int = getStreamTypeInternal()

    @Deprecated("Use getVolumeControlStream() instead.")
    @UnstableApi
    public fun getStreamType(): Int = getStreamTypeInternal()

    private fun getStreamTypeInternal(): Int {
        if ((flags and C.FLAG_AUDIBILITY_ENFORCED) == C.FLAG_AUDIBILITY_ENFORCED) {
            return C.STREAM_TYPE_SYSTEM
        }
        return when (usage) {
            C.USAGE_ASSISTANCE_SONIFICATION -> C.STREAM_TYPE_SYSTEM
            C.USAGE_VOICE_COMMUNICATION -> C.STREAM_TYPE_VOICE_CALL
            C.USAGE_VOICE_COMMUNICATION_SIGNALLING -> C.STREAM_TYPE_DTMF
            C.USAGE_ALARM -> C.STREAM_TYPE_ALARM
            C.USAGE_NOTIFICATION_RINGTONE -> C.STREAM_TYPE_RING
            C.USAGE_NOTIFICATION,
            C.USAGE_NOTIFICATION_COMMUNICATION_REQUEST,
            C.USAGE_NOTIFICATION_COMMUNICATION_INSTANT,
            C.USAGE_NOTIFICATION_COMMUNICATION_DELAYED,
            C.USAGE_NOTIFICATION_EVENT -> C.STREAM_TYPE_NOTIFICATION
            C.USAGE_ASSISTANCE_ACCESSIBILITY -> C.STREAM_TYPE_ACCESSIBILITY
            else -> C.STREAM_TYPE_MUSIC
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AudioAttributes) return false
        return contentType == other.contentType &&
                flags == other.flags &&
                usage == other.usage &&
                allowedCapturePolicy == other.allowedCapturePolicy &&
                spatializationBehavior == other.spatializationBehavior &&
                isContentSpatialized == other.isContentSpatialized &&
                hapticChannelsMuted == other.hapticChannelsMuted
    }

    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + contentType
        result = 31 * result + flags
        result = 31 * result + usage
        result = 31 * result + allowedCapturePolicy
        result = 31 * result + spatializationBehavior
        result = 31 * result + if (isContentSpatialized) 1 else 0
        result = 31 * result + if (hapticChannelsMuted) 1 else 0
        return result
    }

    /** Builder for [AudioAttributes]. */
    public class Builder() {
        private var contentType: Int = C.AUDIO_CONTENT_TYPE_UNKNOWN
        private var flags: Int = C.AUDIO_FLAGS_NONE
        private var usage: Int = C.USAGE_MEDIA
        private var allowedCapturePolicy: Int = C.ALLOW_CAPTURE_BY_ALL
        private var spatializationBehavior: Int = C.SPATIALIZATION_BEHAVIOR_AUTO
        private var isContentSpatialized: Boolean = false
        private var hapticChannelsMuted: Boolean = true

        public constructor(other: AudioAttributes) : this() {
            contentType = other.contentType
            flags = other.flags
            usage = other.usage
            allowedCapturePolicy = other.allowedCapturePolicy
            spatializationBehavior = other.spatializationBehavior
            isContentSpatialized = other.isContentSpatialized
            hapticChannelsMuted = other.hapticChannelsMuted
        }

        public fun setContentType(contentType: Int): Builder = apply { this.contentType = contentType }
        public fun setFlags(flags: Int): Builder = apply { this.flags = flags }
        public fun setUsage(usage: Int): Builder = apply { this.usage = usage }
        public fun setAllowedCapturePolicy(allowedCapturePolicy: Int): Builder = apply { this.allowedCapturePolicy = allowedCapturePolicy }
        public fun setSpatializationBehavior(spatializationBehavior: Int): Builder = apply { this.spatializationBehavior = spatializationBehavior }
        @UnstableApi
        public fun setIsContentSpatialized(isContentSpatialized: Boolean): Builder = apply { this.isContentSpatialized = isContentSpatialized }
        @UnstableApi
        public fun setHapticChannelsMuted(hapticChannelsMuted: Boolean): Builder = apply { this.hapticChannelsMuted = hapticChannelsMuted }

        public fun build(): AudioAttributes = AudioAttributes(
            contentType,
            flags,
            usage,
            allowedCapturePolicy,
            spatializationBehavior,
            isContentSpatialized,
            hapticChannelsMuted
        )
    }

    companion object {
        public val DEFAULT: AudioAttributes = Builder().build()
    }
}
