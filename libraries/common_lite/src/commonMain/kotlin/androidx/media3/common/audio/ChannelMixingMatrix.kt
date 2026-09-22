/*
 * Copyright 2022 The Android Open Source Project
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

import androidx.media3.common.util.UnstableApi

/**
 * An immutable matrix that describes the mapping of input channels to output channels in Pure Kotlin.
 */
@UnstableApi
public class ChannelMixingMatrix(
    public val inputChannelCount: Int,
    public val outputChannelCount: Int,
    coefficients: FloatArray
) {

    public val coefficients: FloatArray = checkCoefficientsValid(coefficients).copyOf()
    public val isZero: Boolean
    public val isDiagonal: Boolean
    public val isIdentity: Boolean

    init {
        require(inputChannelCount > 0) { "Input channel count must be positive." }
        require(outputChannelCount > 0) { "Output channel count must be positive." }
        require(coefficients.size == inputChannelCount * outputChannelCount) { "Coefficient array length is invalid." }

        var allDiagonalOne = true
        var allZero = true
        var allNonDiagonalZero = true

        for (row in 0 until inputChannelCount) {
            for (col in 0 until outputChannelCount) {
                val coeff = getMixingCoefficient(row, col)
                val onDiagonal = (row == col)
                if (coeff != 1f && onDiagonal) allDiagonalOne = false
                if (coeff != 0f) {
                    allZero = false
                    if (!onDiagonal) allNonDiagonalZero = false
                }
            }
        }
        isZero = allZero
        isDiagonal = isSquare() && allNonDiagonalZero
        isIdentity = isDiagonal && allDiagonalOne
    }

    public fun getMixingCoefficient(inputChannel: Int, outputChannel: Int): Float =
        coefficients[inputChannel * outputChannelCount + outputChannel]

    public fun isSquare(): Boolean = inputChannelCount == outputChannelCount

    public fun scaleBy(scale: Float): ChannelMixingMatrix {
        val scaled = FloatArray(coefficients.size) { coefficients[it] * scale }
        return ChannelMixingMatrix(inputChannelCount, outputChannelCount, scaled)
    }

    companion object {
        private fun checkCoefficientsValid(coefficients: FloatArray): FloatArray {
            for (i in coefficients.indices) {
                require(coefficients[i] >= 0f) { "Coefficient at index $i is negative." }
            }
            return coefficients
        }

        public fun createForConstantGain(inputChannelCount: Int, outputChannelCount: Int): ChannelMixingMatrix {
            if (inputChannelCount == outputChannelCount) {
                return ChannelMixingMatrix(inputChannelCount, outputChannelCount, createIdentityCoefficients(outputChannelCount))
            }
            if (inputChannelCount == 1 && outputChannelCount == 2) {
                return ChannelMixingMatrix(1, 2, floatArrayOf(1f, 1f))
            }
            if (inputChannelCount == 2 && outputChannelCount == 1) {
                return ChannelMixingMatrix(2, 1, floatArrayOf(0.5f, 0.5f))
            }
            throw UnsupportedOperationException("Mixing $inputChannelCount -> $outputChannelCount not supported.")
        }

        private fun createIdentityCoefficients(channelCount: Int): FloatArray {
            val coeffs = FloatArray(channelCount * channelCount)
            for (c in 0 until channelCount) {
                coeffs[channelCount * c + c] = 1f
            }
            return coeffs
        }
    }
}
