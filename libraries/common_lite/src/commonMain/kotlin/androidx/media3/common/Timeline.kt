/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.media3.common

/**
 * A representation of a media timeline.
 */
abstract class Timeline {

    abstract val isEmpty: Boolean

    abstract fun getPeriod(periodIndex: Int, period: Period, setIds: Boolean): Period

    abstract fun getIndexOfPeriod(uid: Any): Int

    class Period {
        var uid: Any? = null
        var windowIndex: Int = 0
    }

    companion object {
        val EMPTY = object : Timeline() {
            override val isEmpty: Boolean = true
            override fun getPeriod(periodIndex: Int, period: Period, setIds: Boolean): Period = throw IndexOutOfBoundsException()
            override fun getIndexOfPeriod(uid: Any): Int = C.INDEX_UNSET
        }
    }
}
