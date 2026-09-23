package androidx.media3.common

/**
 * 媒體元資料 (精簡自 Media3 MediaMetadata.java)
 */
data class MediaMetadata(
    @JvmField val title: CharSequence? = null,
    @JvmField val artist: CharSequence? = null,
    @JvmField val albumTitle: CharSequence? = null,
    @JvmField val albumArtist: CharSequence? = null,
    @JvmField val displayTitle: CharSequence? = null,
    @JvmField val subtitle: CharSequence? = null,
    @JvmField val description: CharSequence? = null
) {
    companion object {
        @JvmField
        val EMPTY = MediaMetadata()
    }

    class Builder {
        private var title: CharSequence? = null
        private var artist: CharSequence? = null

        fun setTitle(title: CharSequence?) = apply { this.title = title }
        fun setArtist(artist: CharSequence?) = apply { this.artist = artist }

        fun build() = MediaMetadata(
            title = title,
            artist = artist
        )
    }
}
