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

import androidx.media3.common.util.UnstableApi

/**
 * A representation of a media timeline in Pure Kotlin KMP.
 */
public abstract class Timeline {

    public open val isEmpty: Boolean
        get() = windowCount == 0

    public open val windowCount: Int
        get() = 0

    public open val periodCount: Int
        get() = 0

    public open fun getWindow(windowIndex: Int, window: Window, defaultPositionProjectionUs: Long = 0): Window = window

    public open fun getPeriod(periodIndex: Int, period: Period, setIds: Boolean = false): Period = period

    public open fun getIndexOfPeriod(uid: Any): Int = C.INDEX_UNSET

    public open fun getPeriodByUid(periodUid: Any, period: Period): Period {
        return getPeriod(getIndexOfPeriod(periodUid), period)
    }

    public open fun getUidOfPeriod(periodIndex: Int): Any {
        val period = Period()
        return getPeriod(periodIndex, period, /* setIds= */ true).uid!!
    }

    public open fun getNextWindowIndex(windowIndex: Int, @Player.RepeatMode repeatMode: Int, shuffleModeEnabled: Boolean): Int {
        return when (repeatMode) {
            Player.REPEAT_MODE_ONE -> windowIndex
            Player.REPEAT_MODE_ALL -> if (windowIndex == getLastWindowIndex(shuffleModeEnabled)) getFirstWindowIndex(shuffleModeEnabled) else windowIndex + 1
            Player.REPEAT_MODE_OFF -> if (windowIndex == getLastWindowIndex(shuffleModeEnabled)) C.INDEX_UNSET else windowIndex + 1
            else -> throw IllegalStateException()
        }
    }

    public open fun getPreviousWindowIndex(windowIndex: Int, @Player.RepeatMode repeatMode: Int, shuffleModeEnabled: Boolean): Int {
        return when (repeatMode) {
            Player.REPEAT_MODE_ONE -> windowIndex
            Player.REPEAT_MODE_ALL -> if (windowIndex == getFirstWindowIndex(shuffleModeEnabled)) getLastWindowIndex(shuffleModeEnabled) else windowIndex - 1
            Player.REPEAT_MODE_OFF -> if (windowIndex == getFirstWindowIndex(shuffleModeEnabled)) C.INDEX_UNSET else windowIndex - 1
            else -> throw IllegalStateException()
        }
    }

    public open fun getFirstWindowIndex(shuffleModeEnabled: Boolean): Int {
        return if (isEmpty) C.INDEX_UNSET else 0
    }

    public open fun getLastWindowIndex(shuffleModeEnabled: Boolean): Int {
        return if (isEmpty) C.INDEX_UNSET else windowCount - 1
    }

    public class Window {
        @JvmField public var uid: Any = SINGLE_WINDOW_UID
        @JvmField public var mediaItem: MediaItem = MediaItem.EMPTY
        @JvmField public var presentationStartTimeMs: Long = C.TIME_UNSET
        @JvmField public var windowStartTimeMs: Long = C.TIME_UNSET
        @JvmField public var isSeekable: Boolean = false
        @JvmField public var isDynamic: Boolean = false
        @JvmField public var defaultPositionUs: Long = 0
        @JvmField public var durationUs: Long = C.TIME_UNSET
        @JvmField public var firstPeriodIndex: Int = 0
        @JvmField public var lastPeriodIndex: Int = 0

        public fun set(
            mediaItem: MediaItem,
            presentationStartTimeMs: Long = C.TIME_UNSET,
            windowStartTimeMs: Long = C.TIME_UNSET,
            isSeekable: Boolean = false,
            isDynamic: Boolean = false,
            defaultPositionUs: Long = 0,
            durationUs: Long = C.TIME_UNSET,
            firstPeriodIndex: Int = 0,
            lastPeriodIndex: Int = 0
        ): Window = apply {
            this.uid = SINGLE_WINDOW_UID
            this.mediaItem = mediaItem
            this.presentationStartTimeMs = presentationStartTimeMs
            this.windowStartTimeMs = windowStartTimeMs
            this.isSeekable = isSeekable
            this.isDynamic = isDynamic
            this.defaultPositionUs = defaultPositionUs
            this.durationUs = durationUs
            this.firstPeriodIndex = firstPeriodIndex
            this.lastPeriodIndex = lastPeriodIndex
        }

        companion object {
            @JvmField
            public val SINGLE_WINDOW_UID: Any = Any()
        }
    }

    public class Period {
        @JvmField public var uid: Any? = null
        @JvmField public var windowIndex: Int = 0

        public fun set(uid: Any?, windowIndex: Int): Period = apply {
            this.uid = uid
            this.windowIndex = windowIndex
        }
    }

    companion object {
        @JvmField
        public val EMPTY: Timeline = object : Timeline() {
            override val isEmpty: Boolean = true
            override val windowCount: Int = 0
            override val periodCount: Int = 0
            override fun getPeriod(periodIndex: Int, period: Period, setIds: Boolean): Period = throw IndexOutOfBoundsException()
            override fun getIndexOfPeriod(uid: Any): Int = C.INDEX_UNSET
        }
    }
}
