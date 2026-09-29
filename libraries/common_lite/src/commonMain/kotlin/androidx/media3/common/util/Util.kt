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

package androidx.media3.common.util

import androidx.media3.common.C
import androidx.media3.common.Player
import kotlin.jvm.JvmStatic
import kotlin.math.abs
import kotlin.math.round

object Util {
    @JvmStatic
    fun shouldEnablePlayPauseButton(player: Player?): Boolean {
        return player != null && player.isCommandAvailable(Player.COMMAND_PLAY_PAUSE)
    }

    @JvmStatic
    fun shouldShowPlayButton(player: Player?): Boolean {
        if (player == null) return true
        return !player.playWhenReady || player.playbackState == Player.STATE_ENDED || player.playbackState == Player.STATE_IDLE
    }

    @JvmStatic
    fun handlePlayPauseButtonAction(player: Player?): Boolean {
        if (player == null) return false
        val playbackState = player.playbackState
        if (playbackState == Player.STATE_IDLE) {
            player.prepare()
            player.play()
            return true
        } else if (playbackState == Player.STATE_ENDED) {
            player.seekTo(0)
            player.play()
            return true
        } else if (player.playWhenReady) {
            player.pause()
            return true
        } else {
            player.play()
            return true
        }
    }

    @JvmStatic
    public fun <T> castNonNull(value: T?): T = value!!

    @JvmStatic
    public fun msToUs(timeMs: Long): Long = if (timeMs == C.TIME_UNSET) C.TIME_UNSET else timeMs * 1000L

    @JvmStatic
    public fun usToMs(timeUs: Long): Long = if (timeUs == C.TIME_UNSET) C.TIME_UNSET else timeUs / 1000L

    @JvmStatic
    public fun constrainValue(amount: Int, min: Int, max: Int): Int = amount.coerceIn(min, max)

    @JvmStatic
    public fun constrainValue(amount: Long, min: Long, max: Long): Long = amount.coerceIn(min, max)

    @JvmStatic
    public fun constrainValue(amount: Float, min: Float, max: Float): Float = amount.coerceIn(min, max)

    @JvmStatic
    public fun postOrRun(handler: Any, runnable: Runnable) { runnable.run() }

    @JvmStatic
    public fun isRunningOnEmulator(): Boolean = false

    @JvmStatic
    public fun <T> contains(array: Array<T>, element: T): Boolean = array.contains(element)

    @JvmStatic
    public fun getByteDepth(encoding: Int): Int = 2

    @JvmStatic
    public fun getInt24(byteArray: ByteArray, offset: Int): Int = 0

    @JvmStatic
    public fun putInt24(byteArray: ByteArray, offset: Int, value: Int) {}

    @JvmStatic
    public fun isEncodingLinearPcm(encoding: Int): Boolean = true

    @JvmStatic
    public fun sampleCountToDurationUs(sampleCount: Long, sampleRate: Int): Long = 0L

    @JvmStatic
    public fun durationUsToSampleCount(durationUs: Long, sampleRate: Int): Int = 0

    @JvmStatic
    public fun getMediaDurationForPlayoutDuration(durationMs: Long, speed: Float): Long = durationMs

    @JvmStatic
    public fun percentFloat(value: Int): Float = 0f

    @JvmStatic
    public fun getMaxPendingFramesCountForMediaCodecDecoders(context: Any, mimeType: String, secure: Int): Int = 0

    @JvmStatic
    public fun isBitmapFactorySupportedMimeType(mimeType: String): Boolean = true

    @JvmStatic
    public fun getFormatSupportString(formatSupport: Int): String = ""

    @JvmStatic
    public fun sum(a: Long, b: Long): Long = a + b

    @JvmStatic
    public fun sum(a: Int, b: Int): Int = a + b

    @JvmStatic
    public fun getTrackTypeString(trackType: Int): String = "trackType_$trackType"

    @JvmStatic
    public fun formatInvariant(format: String, vararg args: Any?): String {
        if (args.isEmpty()) return format
        val result = StringBuilder()
        var argIndex = 0
        var i = 0
        while (i < format.length) {
            val c = format[i]
            if (c == '%' && i + 1 < format.length) {
                if (format[i + 1] == '%') {
                    result.append('%')
                    i += 2
                    continue
                }
                var j = i + 1
                var showPlus = false
                var padZero = false
                var width = -1
                var precision = -1

                if (j < format.length && format[j] == '+') {
                    showPlus = true
                    j++
                }
                if (j < format.length && format[j] == '0') {
                    padZero = true
                    j++
                }
                val numStart = j
                while (j < format.length && format[j].isDigit()) {
                    j++
                }
                if (j > numStart) {
                    width = format.substring(numStart, j).toIntOrNull() ?: -1
                }
                if (j < format.length && format[j] == '.') {
                    j++
                    val precStart = j
                    while (j < format.length && format[j].isDigit()) {
                        j++
                    }
                    if (j > precStart) {
                        precision = format.substring(precStart, j).toIntOrNull() ?: -1
                    }
                }

                if (j < format.length) {
                    val spec = format[j]
                    val arg = if (argIndex < args.size) args[argIndex++] else null
                    val formattedArg = formatArgument(arg, spec, showPlus, padZero, width, precision)
                    result.append(formattedArg)
                    i = j + 1
                    continue
                }
            }
            result.append(c)
            i++
        }
        return result.toString()
    }

    private fun formatArgument(
        arg: Any?,
        spec: Char,
        showPlus: Boolean,
        padZero: Boolean,
        width: Int,
        precision: Int
    ): String {
        return when (spec) {
            's', 'S' -> {
                val str = arg?.toString() ?: "null"
                if (width > 0) {
                    if (padZero) str.padStart(width, '0') else str.padStart(width, ' ')
                } else str
            }
            'd' -> {
                val num = (arg as? Number)?.toLong() ?: 0L
                val isNegative = num < 0
                val absNumStr = abs(num).toString()
                val prefix = if (isNegative) "-" else if (showPlus) "+" else ""
                if (width > 0) {
                    val targetLen = width - prefix.length
                    val paddedNum = if (padZero) absNumStr.padStart(targetLen, '0') else absNumStr.padStart(targetLen, ' ')
                    "$prefix$paddedNum"
                } else {
                    "$prefix$absNumStr"
                }
            }
            'x', 'X' -> {
                val num = when (arg) {
                    is Number -> arg.toLong()
                    else -> 0L
                }
                val hexStr = num.toString(16)
                val uppercase = spec == 'X'
                val finalHex = if (uppercase) hexStr.uppercase() else hexStr.lowercase()
                if (width > 0) {
                    if (padZero) finalHex.padStart(width, '0') else finalHex.padStart(width, ' ')
                } else finalHex
            }
            'f', 'F' -> {
                val doubleVal = (arg as? Number)?.toDouble() ?: 0.0
                val isNegative = doubleVal < 0 || (doubleVal == 0.0 && (1.0 / doubleVal).isInfinite() && (1.0 / doubleVal) < 0)
                val absVal = abs(doubleVal)
                val prec = if (precision >= 0) precision else 6
                val formattedVal = formatFloatSimple(absVal, prec)
                val prefix = if (isNegative) "-" else if (showPlus) "+" else ""
                val str = "$prefix$formattedVal"
                if (width > 0) {
                    if (padZero) str.padStart(width, '0') else str.padStart(width, ' ')
                } else str
            }
            else -> arg?.toString() ?: ""
        }
    }

    private fun formatFloatSimple(value: Double, precision: Int): String {
        if (value.isNaN()) return "NaN"
        if (value.isInfinite()) return "Infinity"
        var factor = 1.0
        repeat(precision) { factor *= 10.0 }
        val rounded = round(value * factor) / factor
        val intPart = rounded.toLong()
        val fracPart = abs(rounded - intPart)
        if (precision == 0) return intPart.toString()
        var fracFactor = 1.0
        repeat(precision) { fracFactor *= 10.0 }
        val fracInt = round(fracPart * fracFactor).toLong()
        val fracStr = fracInt.toString().padStart(precision, '0')
        return "$intPart.$fracStr"
    }

    @JvmStatic
    public fun getStringForTime(timeMs: Long): String {
        var time = if (timeMs == C.TIME_UNSET) 0 else timeMs
        val prefix = if (time < 0) "-" else ""
        time = abs(time)
        val totalSeconds = (time + 500) / 1000
        val seconds = (totalSeconds % 60).toString().padStart(2, '0')
        val minutes = ((totalSeconds / 60) % 60).toString().padStart(2, '0')
        val hours = totalSeconds / 3600
        return if (hours > 0) {
            "$prefix$hours:$minutes:$seconds"
        } else {
            "$prefix$minutes:$seconds"
        }
    }

    @JvmStatic
    public fun getStringForTime(builder: StringBuilder?, formatter: Any?, timeMs: Long): String {
        val result = getStringForTime(timeMs)
        if (builder != null) {
            builder.setLength(0)
            builder.append(result)
        }
        return result
    }
}
