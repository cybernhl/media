/*
 * Copyright 2023 The Android Open Source Project
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

/**
 * Array-based unbounded queue for long primitives with amortized O(1) add and remove.
 */
@UnstableApi
public class LongArrayQueue(minCapacity: Int = DEFAULT_INITIAL_CAPACITY) {

    private var headIndex: Int = 0
    private var tailIndex: Int = -1
    private var size: Int = 0
    private var data: LongArray
    private var wrapAroundMask: Int

    init {
        require(minCapacity in 0..(1 shl 30))
        var cap = if (minCapacity == 0) 1 else minCapacity
        if (cap.countOneBits() != 1) {
            cap = (1 shl (32 - (cap - 1).countLeadingZeroBits()))
        }
        data = LongArray(cap)
        wrapAroundMask = data.size - 1
    }

    public fun add(value: Long) {
        if (size == data.size) {
            doubleArraySize()
        }
        tailIndex = (tailIndex + 1) and wrapAroundMask
        data[tailIndex] = value
        size++
    }

    public fun remove(): Long {
        if (size == 0) {
            throw NoSuchElementException("Queue is empty")
        }
        val value = data[headIndex]
        headIndex = (headIndex + 1) and wrapAroundMask
        size--
        return value
    }

    public fun element(): Long {
        if (size == 0) {
            throw NoSuchElementException("Queue is empty")
        }
        return data[headIndex]
    }

    public fun size(): Int = size

    public fun isEmpty(): Boolean = size == 0

    public fun clear() {
        headIndex = 0
        tailIndex = -1
        size = 0
    }

    private fun doubleArraySize() {
        val newCapacity = data.size shl 1
        check(newCapacity > 0)
        val newData = LongArray(newCapacity)
        val itemsToRight = data.size - headIndex
        val itemsToLeft = headIndex
        data.copyInto(newData, 0, headIndex, headIndex + itemsToRight)
        data.copyInto(newData, itemsToRight, 0, itemsToLeft)

        headIndex = 0
        tailIndex = size - 1
        data = newData
        wrapAroundMask = data.size - 1
    }

    companion object {
        public const val DEFAULT_INITIAL_CAPACITY: Int = 16
    }
}
