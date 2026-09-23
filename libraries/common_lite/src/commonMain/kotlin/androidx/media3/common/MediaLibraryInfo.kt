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
package androidx.media3.common

import androidx.media3.common.util.UnstableApi

/**
 * Information about the media libraries in Pure Kotlin KMP.
 * Fully aligned with androidx.media3.common.MediaLibraryInfo.
 */
@UnstableApi
public object MediaLibraryInfo {

    public const val TAG: String = "AndroidXMedia3"
    public const val VERSION: String = "1.11.1"
    public const val VERSION_SLASHY: String = "AndroidXMedia3/1.11.1"
    public const val VERSION_INT: Int = 1_011_001_3_00
    public const val TRACE_ENABLED: Boolean = true
    public const val INTERFACE_VERSION: Int = 10

    private var enableWorkarounds: Boolean = true

    public fun enableWorkarounds(): Boolean = enableWorkarounds

    public fun setEnableWorkarounds(enable: Boolean) {
        enableWorkarounds = enable
    }

    private val registeredModulesSet = mutableSetOf<String>()
    private var registeredModulesString = "media3.common"

    public fun registeredModules(): String = registeredModulesString

    public fun registerModule(name: String) {
        if (registeredModulesSet.add(name)) {
            registeredModulesString = "$registeredModulesString, $name"
        }
    }
}
