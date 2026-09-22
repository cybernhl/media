/*
 * Copyright (C) 2014 The Android Open Source Project
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

/** An interface through which system clocks can be read in Pure Kotlin KMP. */
@UnstableApi
public interface Clock {

    public fun currentTimeMillis(): Long

    public fun elapsedRealtime(): Long

    public fun uptimeMillis(): Long

    public fun nanoTime(): Long

    public fun onThreadBlocked()
}
