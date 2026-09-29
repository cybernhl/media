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

import kotlin.jvm.JvmStatic

/** Provides methods for asserting the truth of expressions and properties. */
@UnstableApi
public object Assertions {

    @JvmStatic
    public fun checkArgument(expression: Boolean) {
        require(expression)
    }

    @JvmStatic
    public fun checkArgument(expression: Boolean, errorMessage: Any) {
        require(expression) { errorMessage.toString() }
    }

    @JvmStatic
    public fun checkState(expression: Boolean) {
        check(expression)
    }

    @JvmStatic
    public fun checkState(expression: Boolean, errorMessage: Any) {
        check(expression) { errorMessage.toString() }
    }

    @JvmStatic
    public fun <T : Any> checkNotNull(reference: T?): T {
        return kotlin.checkNotNull(reference)
    }

    @JvmStatic
    public fun <T : Any> checkNotNull(reference: T?, errorMessage: Any): T {
        return kotlin.checkNotNull(reference) { errorMessage.toString() }
    }

    @JvmStatic
    public fun checkNotEmpty(string: String?): String {
        require(!string.isNullOrEmpty())
        return string
    }
}
