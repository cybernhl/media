/*
 * Copyright 2025 The Android Open Source Project
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
 * A circular int array offering simple queue access without integer boxing.
 */
@UnstableApi
public class CircularIntArray {

    private var elements: IntArray = IntArray(8)
    private var head: Int = 0
    private var tail: Int = 0
    private var capacityBitmask: Int = 7

    public fun addLast(e: Int) {
        elements[tail] = e
        tail = (tail + 1) and capacityBitmask
        if (tail == head) {
            doubleCapacity()
        }
    }

    public fun popFirst(): Int {
        if (head == tail) {
            throw IndexOutOfBoundsException()
        }
        val result = elements[head]
        head = (head + 1) and capacityBitmask
        return result
    }

    public fun clear() {
        tail = head
    }

    public fun isEmpty(): Boolean = head == tail

    private fun doubleCapacity() {
        val n = elements.size
        val r = n - head
        val newCapacity = n shl 1
        val a = IntArray(newCapacity)
        elements.copyInto(a, 0, head, head + r)
        elements.copyInto(a, r, 0, head)
        elements = a
        head = 0
        tail = n
        capacityBitmask = newCapacity - 1
    }
}
