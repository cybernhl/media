package androidx.media3.common

/**
 * 媒體項目資訊 (極精簡版，僅保留必要欄位)
 */
data class MediaItem(
    val mediaId: String,
    val localConfiguration: LocalConfiguration? = null,
    val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY
) {
    data class LocalConfiguration(
        val uri: String,
        val mimeType: String? = null
    )

    companion object {
        fun fromUri(uri: String): MediaItem {
            return MediaItem(
                mediaId = uri,
                localConfiguration = LocalConfiguration(uri = uri)
            )
        }
    }

    class Builder {
        private var mediaId: String? = null
        private var uri: String? = null
        private var mediaMetadata: MediaMetadata = MediaMetadata.EMPTY

        fun setMediaId(mediaId: String) = apply { this.mediaId = mediaId }
        fun setUri(uri: String?) = apply { this.uri = uri }
        fun setMediaMetadata(mediaMetadata: MediaMetadata) = apply { this.mediaMetadata = mediaMetadata }

        fun build(): MediaItem {
            val finalUri = uri ?: ""
            return MediaItem(
                mediaId = mediaId ?: finalUri,
                localConfiguration = LocalConfiguration(uri = finalUri),
                mediaMetadata = mediaMetadata
            )
        }
    }
}
