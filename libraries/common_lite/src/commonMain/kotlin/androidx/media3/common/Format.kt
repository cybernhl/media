package androidx.media3.common

import androidx.media3.common.util.UnstableApi

/**
 * 媒體格式定義，完全對齊 androidx.media3.common.Format。
 * 這是實現 common 與 common-lite 互換性的核心類別。
 */
class Format private constructor(builder: Builder) {
    
    @JvmField val id: String? = builder.id
    @JvmField val label: String? = builder.label
    @JvmField val selectionFlags: Int = builder.selectionFlags
    @JvmField val roleFlags: Int = builder.roleFlags
    @JvmField val bitrate: Int = builder.bitrate
    @JvmField val codecs: String? = builder.codecs
    
    // Container/Sample specific
    @JvmField val containerMimeType: String? = builder.containerMimeType
    @JvmField val sampleMimeType: String? = builder.sampleMimeType
    @JvmField val maxInputSize: Int = builder.maxInputSize

    // Video specific
    @JvmField val width: Int = builder.width
    @JvmField val height: Int = builder.height
    @JvmField val frameRate: Float = builder.frameRate
    @JvmField val rotationDegrees: Int = builder.rotationDegrees
    @JvmField val pixelWidthHeightRatio: Float = builder.pixelWidthHeightRatio
    @JvmField val colorInfo: ColorInfo? = builder.colorInfo

    // Audio specific
    @JvmField val channelCount: Int = builder.channelCount
    @JvmField val sampleRate: Int = builder.sampleRate
    @JvmField val pcmEncoding: Int = builder.pcmEncoding
    @JvmField val encoderDelay: Int = builder.encoderDelay
    @JvmField val encoderPadding: Int = builder.encoderPadding

    @UnstableApi
    class Builder {
        var id: String? = null
        var label: String? = null
        var selectionFlags: Int = 0
        var roleFlags: Int = 0
        var bitrate: Int = -1
        var codecs: String? = null
        
        var containerMimeType: String? = null
        var sampleMimeType: String? = null
        var maxInputSize: Int = -1

        var width: Int = -1
        var height: Int = -1
        var frameRate: Float = -1f
        var rotationDegrees: Int = 0
        var pixelWidthHeightRatio: Float = 1.0f
        var colorInfo: ColorInfo? = null

        var channelCount: Int = -1
        var sampleRate: Int = -1
        var pcmEncoding: Int = -1
        var encoderDelay: Int = 0
        var encoderPadding: Int = 0

        constructor()
        
        internal constructor(format: Format) {
            this.id = format.id
            this.label = format.label
            this.selectionFlags = format.selectionFlags
            this.roleFlags = format.roleFlags
            this.bitrate = format.bitrate
            this.codecs = format.codecs
            this.containerMimeType = format.containerMimeType
            this.sampleMimeType = format.sampleMimeType
            this.maxInputSize = format.maxInputSize
            this.width = format.width
            this.height = format.height
            this.frameRate = format.frameRate
            this.rotationDegrees = format.rotationDegrees
            this.pixelWidthHeightRatio = format.pixelWidthHeightRatio
            this.colorInfo = format.colorInfo
            this.channelCount = format.channelCount
            this.sampleRate = format.sampleRate
            this.pcmEncoding = format.pcmEncoding
            this.encoderDelay = format.encoderDelay
            this.encoderPadding = format.encoderPadding
        }

        fun setId(id: String?) = apply { this.id = id }
        fun setSampleMimeType(sampleMimeType: String?) = apply { this.sampleMimeType = sampleMimeType }
        fun setCodecs(codecs: String?) = apply { this.codecs = codecs }
        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setFrameRate(frameRate: Float) = apply { this.frameRate = frameRate }
        fun setChannelCount(channelCount: Int) = apply { this.channelCount = channelCount }
        fun setSampleRate(sampleRate: Int) = apply { this.sampleRate = sampleRate }
        fun setPcmEncoding(pcmEncoding: Int) = apply { this.pcmEncoding = pcmEncoding }

        fun build(): Format = Format(this)
    }

    fun buildUpon(): Builder = Builder(this)

    companion object {
        const val NO_VALUE = -1
    }
}
