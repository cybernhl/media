package androidx.media3.common.audio

import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * 音訊處理器介面，對齊 androidx.media3.common.audio.AudioProcessor。
 */
interface AudioProcessor {
    companion object {
        val EMPTY_BUFFER: ByteBuffer = ByteBuffer.allocateDirect(0).order(ByteOrder.nativeOrder())
    }

    class AudioFormat(val sampleRate: Int, val channelCount: Int, val encoding: Int) {
        companion object {
            val NOT_SET = AudioFormat(-1, -1, -1)
        }
    }

    class UnhandledAudioFormatException(format: AudioFormat) : Exception("Unhandled format: $format")

    fun configure(inputAudioFormat: AudioFormat): AudioFormat
    fun isActive(): Boolean
    fun queueInput(inputBuffer: ByteBuffer)
    fun queueEndOfStream()
    fun getOutput(): ByteBuffer
    fun isEnded(): Boolean
    fun flush()
    fun reset()
}
