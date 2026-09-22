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
package androidx.media3.common.audio

import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi

/**
 * Converts different PCM audio encodings to 16-bit integer PCM in Pure Kotlin.
 */
@UnstableApi
public class ToInt16PcmAudioProcessor {

    public fun isEncodingSupported(encoding: Int): Boolean = when (encoding) {
        C.ENCODING_PCM_8BIT,
        C.ENCODING_PCM_16BIT,
        C.ENCODING_PCM_16BIT_BIG_ENDIAN,
        C.ENCODING_PCM_24BIT,
        C.ENCODING_PCM_32BIT,
        C.ENCODING_PCM_FLOAT -> true
        else -> false
    }

    public fun resampleTo16BitPcm(input: ByteArray, encoding: Int): ByteArray {
        if (encoding == C.ENCODING_PCM_16BIT) return input
        require(isEncodingSupported(encoding)) { "Unsupported PCM encoding: $encoding" }

        return when (encoding) {
            C.ENCODING_PCM_8BIT -> {
                val output = ByteArray(input.size * 2)
                for (i in input.indices) {
                    output[i * 2] = 0
                    output[i * 2 + 1] = ((input[i].toInt() and 0xFF) - 128).toByte()
                }
                output
            }
            C.ENCODING_PCM_16BIT_BIG_ENDIAN -> {
                val output = ByteArray(input.size)
                for (i in input.indices step 2) {
                    output[i] = input[i + 1]
                    output[i + 1] = input[i]
                }
                output
            }
            C.ENCODING_PCM_24BIT -> {
                val output = ByteArray((input.size / 3) * 2)
                var outIdx = 0
                for (i in input.indices step 3) {
                    output[outIdx++] = input[i + 1]
                    output[outIdx++] = input[i + 2]
                }
                output
            }
            else -> input
        }
    }
}
