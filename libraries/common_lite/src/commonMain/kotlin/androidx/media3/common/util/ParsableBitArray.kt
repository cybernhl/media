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

import kotlin.math.min

/** Wraps a byte array, providing methods that allow it to be read as a bitstream. */
@UnstableApi
public class ParsableBitArray {

    public var data: ByteArray = ByteArray(0)
    private var byteOffset: Int = 0
    private var bitOffset: Int = 0
    private var byteLimit: Int = 0

    public constructor()

    public constructor(data: ByteArray) : this(data, data.size)

    public constructor(data: ByteArray, limit: Int) {
        this.data = data
        this.byteLimit = limit
    }

    public fun reset(data: ByteArray) {
        reset(data, data.size)
    }

    public fun reset(parsableByteArray: ParsableByteArray) {
        reset(parsableByteArray.data, parsableByteArray.limit)
        setPosition(parsableByteArray.position * 8)
    }

    public fun reset(data: ByteArray, limit: Int) {
        this.data = data
        this.byteOffset = 0
        this.bitOffset = 0
        this.byteLimit = limit
    }

    public fun bitsLeft(): Int = (byteLimit - byteOffset) * 8 - bitOffset

    public fun getPosition(): Int = byteOffset * 8 + bitOffset

    public fun getBytePosition(): Int {
        check(bitOffset == 0) { "Not byte aligned" }
        return byteOffset
    }

    public fun setPosition(position: Int) {
        byteOffset = position / 8
        bitOffset = position - (byteOffset * 8)
    }

    public fun skipBit() {
        if (++bitOffset == 8) {
            bitOffset = 0
            byteOffset++
        }
    }

    public fun skipBits(numBits: Int) {
        val numBytes = numBits / 8
        byteOffset += numBytes
        bitOffset += numBits - (numBytes * 8)
        if (bitOffset > 7) {
            byteOffset++
            bitOffset -= 8
        }
    }

    public fun readBit(): Boolean {
        val returnValue = (data[byteOffset].toInt() and (0x80 shr bitOffset)) != 0
        skipBit()
        return returnValue
    }

    public fun readBits(numBits: Int): Int {
        if (numBits == 0) return 0
        var returnValue = 0
        bitOffset += numBits
        while (bitOffset > 8) {
            bitOffset -= 8
            returnValue = returnValue or ((data[byteOffset].toInt() and 0xFF) shl bitOffset)
            byteOffset++
        }
        returnValue = returnValue or ((data[byteOffset].toInt() and 0xFF) shr (8 - bitOffset))
        returnValue = returnValue and (-1 ushr (32 - numBits))
        if (bitOffset == 8) {
            bitOffset = 0
            byteOffset++
        }
        return returnValue
    }
}
