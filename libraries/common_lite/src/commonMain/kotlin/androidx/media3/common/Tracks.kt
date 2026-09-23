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
 * Information about tracks and which are selected.
 */
class Tracks(private val groups: List<Group> = emptyList()) {

    val isEmpty: Boolean = groups.isEmpty()

    fun isTypeSelected(trackType: Int): Boolean {
        return groups.any { it.trackType == trackType && it.isSelected }
    }

    class Group(val trackType: Int, val isSelected: Boolean)

    companion object {
        @JvmField
        val EMPTY = Tracks()
    }
}
