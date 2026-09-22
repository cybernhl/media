/*
 * Copyright (C) 2018 The Android Open Source Project
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

import kotlin.math.abs

/** A utility class to keep a queue of values with timestamps. Thread-safe in KMP via atomic/locking if needed. */
@UnstableApi
public class TimedValueQueue<V>(initialBufferSize: Int = INITIAL_BUFFER_SIZE) {

    private var timestamps: LongArray = LongArray(initialBufferSize)
    @Suppress("UNCHECKED_CAST")
    private var values: Array<V?> = arrayOfNulls<Any>(initialBufferSize) as Array<V?>
    private var first: Int = 0
    private var size: Int = 0

    public fun add(timestamp: Long, value: V) {
        clearBufferOnTimeDiscontinuity(timestamp)
        doubleCapacityIfFull()
        addUnchecked(timestamp, value)
    }

    public fun clear() {
        first = 0
        size = 0
        values.fill(null)
    }

    public fun size(): Int = size

    public fun pollFirst(): V? = if (size == 0) null else popFirst()

    public fun pollFloor(timestamp: Long): V? = poll(timestamp, onlyOlder = true)

    public fun poll(timestamp: Long): V? = poll(timestamp, onlyOlder = false)

    private fun poll(timestamp: Long, onlyOlder: Boolean): V? {
        var value: V? = null
        var previousTimeDiff = Long.MAX_VALUE
        while (size > 0) {
            val timeDiff = timestamp - timestamps[first]
            if (timeDiff < 0 && (onlyOlder || -timeDiff >= previousTimeDiff)) {
                break
            }
            previousTimeDiff = timeDiff
            value = popFirst()
        }
        return value
    }

    private fun popFirst(): V? {
        check(size > 0)
        val value = values[first]
        values[first] = null
        first = (first + 1) % values.size
        size--
        return value
    }

    private fun clearBufferOnTimeDiscontinuity(timestamp: Long) {
        if (size > 0) {
            val last = (first + size - 1) % values.size
            if (timestamp <= timestamps[last]) {
                clear()
            }
        }
    }

    private fun doubleCapacityIfFull() {
        val capacity = values.size
        if (size < capacity) return
        val newCapacity = capacity * 2
        val newTimestamps = LongArray(newCapacity)
        @Suppress("UNCHECKED_CAST")
        val newValues = arrayOfNulls<Any>(newCapacity) as Array<V?>

        val length = capacity - first
        timestamps.copyInto(newTimestamps, 0, first, first + length)
        values.copyInto(newValues, 0, first, first + length)
        if (first > 0) {
            timestamps.copyInto(newTimestamps, length, 0, first)
            values.copyInto(newValues, length, 0, first)
        }
        timestamps = newTimestamps
        values = newValues
        first = 0
    }

    private fun addUnchecked(timestamp: Long, value: V) {
        val next = (first + size) % values.size
        timestamps[next] = timestamp
        values[next] = value
        size++
    }

    companion object {
        private const val INITIAL_BUFFER_SIZE: Int = 10
    }
}
