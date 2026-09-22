package androidx.media3.common

import androidx.media3.common.util.UnstableApi

/**
 * 音訊屬性 (精簡自 Media3 AudioAttributes.java)
 */
class AudioAttributes private constructor(
    val contentType: Int,
    val flags: Int,
    val usage: Int,
    val allowedCapturePolicy: Int,
    val spatializationBehavior: Int
) {
    /** Builder for [AudioAttributes]. */
    class Builder {
        private var contentType = C.CONTENT_TYPE_UNKNOWN
        private var flags = C.AUDIO_FLAGS_NONE
        private var usage = C.USAGE_MEDIA
        private var allowedCapturePolicy = 0
        private var spatializationBehavior = 0

        fun setContentType(contentType: Int) = apply { this.contentType = contentType }
        fun setFlags(flags: Int) = apply { this.flags = flags }
        fun setUsage(usage: Int) = apply { this.usage = usage }
        fun setAllowedCapturePolicy(allowedCapturePolicy: Int) = apply {
            this.allowedCapturePolicy = allowedCapturePolicy
        }
        fun setSpatializationBehavior(spatializationBehavior: Int) = apply {
            this.spatializationBehavior = spatializationBehavior
        }

        fun build() = AudioAttributes(
            contentType,
            flags,
            usage,
            allowedCapturePolicy,
            spatializationBehavior
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AudioAttributes) return false
        return contentType == other.contentType &&
                flags == other.flags &&
                usage == other.usage &&
                allowedCapturePolicy == other.allowedCapturePolicy &&
                spatializationBehavior == other.spatializationBehavior
    }

    override fun hashCode(): Int {
        var result = contentType
        result = 31 * result + flags
        result = 31 * result + usage
        result = 31 * result + allowedCapturePolicy
        result = 31 * result + spatializationBehavior
        return result
    }

    companion object {
        val DEFAULT = Builder().build()
    }
}
