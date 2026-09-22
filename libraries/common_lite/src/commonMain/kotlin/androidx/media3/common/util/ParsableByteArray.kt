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

/**
 * Wraps a byte array, providing a set of methods for parsing data from it. Numerical values are
 * parsed with the assumption that their constituent bytes are in big endian order.
 */
@UnstableApi
public class ParsableByteArray {

    var data: ByteArray
        private set

    var position: Int = 0
        set(value) {
            require(value in 0..limit) { "Position $value out of bounds [0, $limit]" }
            field = value
        }

    var limit: Int = 0
        set(value) {
            require(value in 0..data.size) { "Limit $value out of bounds [0, ${data.size}]" }
            field = value
        }

    public constructor() {
        data = ByteArray(0)
        limit = 0
    }

    public constructor(limit: Int) {
        data = ByteArray(limit)
        this.limit = limit
    }

    public constructor(data: ByteArray) {
        this.data = data
        this.limit = data.size
    }

    public constructor(data: ByteArray, limit: Int) {
        this.data = data
        this.limit = limit
    }

    public fun reset(limit: Int) {
        reset(if (capacity() < limit) ByteArray(limit) else data, limit)
    }

    public fun reset(data: ByteArray) {
        reset(data, data.size)
    }

    public fun reset(data: ByteArray, limit: Int) {
        this.data = data
        this.limit = limit
        this.position = 0
    }

    public fun bytesLeft(): Int = limit - position

    public fun capacity(): Int = data.size

    public fun skipBytes(bytes: Int) {
        position += bytes
    }

    public fun readBytes(buffer: ByteArray, offset: Int, length: Int) {
        data.copyInto(buffer, offset, position, position + length)
        position += length
    }

    public fun readUnsignedByte(): Int = data[position++].toInt() and 0xFF

    public fun readUnsignedShort(): Int =
        ((data[position++].toInt() and 0xFF) shl 8) or
        (data[position++].toInt() and 0xFF)

    public fun readUnsignedInt(): Long =
        ((data[position++].toLong() and 0xFF) shl 24) or
        ((data[position++].toLong() and 0xFF) shl 16) or
        ((data[position++].toLong() and 0xFF) shl 8) or
        (data[position++].toLong() and 0xFF)

    public fun readInt(): Int =
        ((data[position++].toInt() and 0xFF) shl 24) or
        ((data[position++].toInt() and 0xFF) shl 16) or
        ((data[position++].toInt() and 0xFF) shl 8) or
        (data[position++].toInt() and 0xFF)

    public fun readLong(): Long =
        ((data[position++].toLong() and 0xFF) shl 56) or
        ((data[position++].toLong() and 0xFF) shl 48) or
        ((data[position++].toLong() and 0xFF) shl 40) or
        ((data[position++].toLong() and 0xFF) shl 32) or
        ((data[position++].toLong() and 0xFF) shl 24) or
        ((data[position++].toLong() and 0xFF) shl 16) or
        ((data[position++].toLong() and 0xFF) shl 8) or
        (data[position++].toLong() and 0xFF)

    public fun readFloat(): Float = Float.fromBits(readInt())

    public fun readDouble(): Double = Double.fromBits(readLong())

    public fun readString(length: Int): String {
        val result = data.decodeToString(position, position + length)
        position += length
        return result
    }

    companion object {
        public const val INVALID_CODE_POINT: Int = 0x11_0000
    }
}
