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

import androidx.media3.common.C

/**
 * Adjusts and offsets sample timestamps. MPEG-2 TS timestamps scaling and adjustment is supported,
 * taking into account timestamp rollover.
 */
@UnstableApi
public class TimestampAdjuster(firstSampleTimestampUs: Long) {

    public var firstSampleTimestampUs: Long = firstSampleTimestampUs
        private set

    public var timestampOffsetUs: Long = C.TIME_UNSET
        private set

    public var lastUnadjustedTimestampUs: Long = C.TIME_UNSET
        private set

    public fun reset(firstSampleTimestampUs: Long) {
        this.firstSampleTimestampUs = firstSampleTimestampUs
        this.timestampOffsetUs = C.TIME_UNSET
        this.lastUnadjustedTimestampUs = C.TIME_UNSET
    }

    public fun adjustTsTimestamp(pts90Khz: Long): Long {
        if (pts90Khz == C.TIME_UNSET) {
            return C.TIME_UNSET
        }
        if (lastUnadjustedTimestampUs != C.TIME_UNSET) {
            val lastPts = usToPts(lastUnadjustedTimestampUs)
            var closestPts = (lastPts + 0x100000000L / 2) / 0x200000000L
            var pts = pts90Khz + closestPts * 0x200000000L
            if (pts < lastPts) {
                pts += 0x200000000L
            }
            return adjustSampleTimestamp(ptsToUs(pts))
        }
        return adjustSampleTimestamp(ptsToUs(pts90Khz))
    }

    public fun adjustSampleTimestamp(timeUs: Long): Long {
        if (timeUs == C.TIME_UNSET) {
            return C.TIME_UNSET
        }
        if (lastUnadjustedTimestampUs != C.TIME_UNSET) {
            lastUnadjustedTimestampUs = timeUs
        } else {
            if (firstSampleTimestampUs != MODE_NO_OFFSET) {
                timestampOffsetUs = firstSampleTimestampUs - timeUs
            }
            lastUnadjustedTimestampUs = timeUs
        }
        return timeUs + timestampOffsetUs
    }

    companion object {
        public const val MODE_NO_OFFSET: Long = Long.MAX_VALUE
        public const val MODE_SHARED: Long = Long.MAX_VALUE - 1

        public fun ptsToUs(pts: Long): Long = (pts * 1_000_000L) / 90_000L

        public fun usToPts(us: Long): Long = (us * 90_000L) / 1_000_000L
    }
}
