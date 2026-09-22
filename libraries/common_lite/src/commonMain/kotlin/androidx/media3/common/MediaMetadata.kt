package androidx.media3.common

/**
 * 媒體元資料 (精簡自 Media3 MediaMetadata.java)
 */
data class MediaMetadata(
    val title: CharSequence? = null,
    val artist: CharSequence? = null,
    val albumTitle: CharSequence? = null,
    val albumArtist: CharSequence? = null,
    val displayTitle: CharSequence? = null,
    val subtitle: CharSequence? = null,
    val description: CharSequence? = null
) {
    companion object {
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
