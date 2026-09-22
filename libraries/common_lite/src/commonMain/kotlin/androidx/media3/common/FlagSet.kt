/*
 * Copyright (C) 2020 The Android Open Source Project
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
package androidx.media3.common

import androidx.media3.common.util.UnstableApi

/** A set of integer flags implemented in Pure Kotlin KMP. */
@UnstableApi
public class FlagSet private constructor(private val flags: IntArray) {

    public fun contains(flag: Int): Boolean = flags.contains(flag)

    public fun containsAny(vararg flagsToCheck: Int): Boolean = flagsToCheck.any { contains(it) }

    public fun containsAny(other: FlagSet): Boolean = other.flags.any { contains(it) }

    public fun size(): Int = flags.size

    public fun get(index: Int): Int = flags[index]

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FlagSet) return false
        return flags.contentEquals(other.flags)
    }

    override fun hashCode(): Int = flags.contentHashCode()

    public class Builder {
        private val flagsSet = mutableSetOf<Int>()
        private var buildCalled = false

        public fun add(flag: Int): Builder {
            check(!buildCalled)
            flagsSet.add(flag)
            return this
        }

        public fun addIf(flag: Int, condition: Boolean): Builder = if (condition) add(flag) else this

        public fun addAll(vararg flags: Int): Builder {
            flags.forEach { add(it) }
            return this
        }

        public fun addAll(flags: FlagSet): Builder {
            for (i in 0 until flags.size()) {
                add(flags.get(i))
            }
            return this
        }

        public fun remove(flag: Int): Builder {
            check(!buildCalled)
            flagsSet.remove(flag)
            return this
        }

        public fun removeIf(flag: Int, condition: Boolean): Builder = if (condition) remove(flag) else this

        public fun build(): FlagSet {
            check(!buildCalled)
            buildCalled = true
            return FlagSet(flagsSet.toIntArray())
        }
    }
}
