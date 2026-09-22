/*
 * Copyright 2021 The Android Open Source Project
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

/** A rating for media content in Pure Kotlin KMP. */
public abstract class Rating {
    public abstract fun isRated(): Boolean

    companion object {
        public const val RATING_UNSET: Float = -1.0f
    }
}

public class HeartRating(
    public val isHeart: Boolean = false,
    private val rated: Boolean = true
) : Rating() {
    public constructor() : this(isHeart = false, rated = false)

    override fun isRated(): Boolean = rated

    override fun equals(other: Any?): Boolean {
        if (other !is HeartRating) return false
        return isHeart == other.isHeart && rated == other.rated
    }

    override fun hashCode(): Int = (rated.hashCode() * 31) + isHeart.hashCode()
}

public class PercentageRating(
    public val percent: Float = RATING_UNSET
) : Rating() {
    init {
        if (percent != RATING_UNSET) {
            require(percent in 0.0f..100.0f) { "percent must be in range [0, 100]" }
        }
    }

    override fun isRated(): Boolean = percent != RATING_UNSET

    override fun equals(other: Any?): Boolean {
        if (other !is PercentageRating) return false
        return percent == other.percent
    }

    override fun hashCode(): Int = percent.hashCode()
}

public class StarRating(
    public val maxStars: Int = 5,
    public val starRating: Float = RATING_UNSET
) : Rating() {
    init {
        require(maxStars > 0) { "maxStars must be positive" }
        if (starRating != RATING_UNSET) {
            require(starRating in 0.0f..maxStars.toFloat()) { "starRating out of range" }
        }
    }

    override fun isRated(): Boolean = starRating != RATING_UNSET

    override fun equals(other: Any?): Boolean {
        if (other !is StarRating) return false
        return maxStars == other.maxStars && starRating == other.starRating
    }

    override fun hashCode(): Int = (maxStars * 31) + starRating.hashCode()
}

public class ThumbRating(
    public val isThumbsUp: Boolean = false,
    private val rated: Boolean = true
) : Rating() {
    public constructor() : this(isThumbsUp = false, rated = false)

    override fun isRated(): Boolean = rated

    override fun equals(other: Any?): Boolean {
        if (other !is ThumbRating) return false
        return isThumbsUp == other.isThumbsUp && rated == other.rated
    }

    override fun hashCode(): Int = (rated.hashCode() * 31) + isThumbsUp.hashCode()
}
