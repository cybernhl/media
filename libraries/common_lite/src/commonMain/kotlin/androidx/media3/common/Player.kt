package androidx.media3.common

/**
 * 播放器介面定義 (比照 Media3 規範)。
 */
interface Player {

    companion object {
        /** 初始狀態或已停止。 */
        const val STATE_IDLE = 1
        /** 緩衝中，暫時無法播放。 */
        const val STATE_BUFFERING = 2
        /** 已準備就緒，可以播放（如果 playWhenReady 為 true 則會開始播放）。 */
        const val STATE_READY = 3
        /** 播放結束。 */
        const val STATE_ENDED = 4

        const val REPEAT_MODE_OFF = 0
        const val REPEAT_MODE_ONE = 1
        const val REPEAT_MODE_ALL = 2

        // 事件類型常數
        const val EVENT_PLAYBACK_STATE_CHANGED = 1
        const val EVENT_PLAY_WHEN_READY_CHANGED = 2
        const val EVENT_IS_PLAYING_CHANGED = 3
        const val EVENT_POSITION_DISCONTINUITY = 4
        const val EVENT_AVAILABLE_COMMANDS_CHANGED = 5
        const val EVENT_REPEAT_MODE_CHANGED = 6
        const val EVENT_SHUFFLE_MODE_ENABLED_CHANGED = 7
        const val EVENT_VOLUME_CHANGED = 8
        const val EVENT_VIDEO_SIZE_CHANGED = 9
        const val EVENT_RENDERED_FIRST_FRAME = 10
        const val EVENT_TRACKS_CHANGED = 11
        const val EVENT_TIMELINE_CHANGED = 12
        const val EVENT_PLAYBACK_PARAMETERS_CHANGED = 13
        const val EVENT_SEEK_BACK_INCREMENT_CHANGED = 14
        const val EVENT_SEEK_FORWARD_INCREMENT_CHANGED = 15

        // Commands
        const val COMMAND_PLAY_PAUSE = 1
        const val COMMAND_GET_CURRENT_MEDIA_ITEM = 2
        const val COMMAND_SET_VIDEO_SURFACE = 3
        const val COMMAND_SEEK_BACK = 4
        const val COMMAND_SEEK_FORWARD = 5
        const val COMMAND_SEEK_TO_NEXT = 6
        const val COMMAND_SEEK_TO_PREVIOUS = 7
        const val COMMAND_GET_TRACKS = 8
        const val COMMAND_GET_TIMELINE = 9
        const val COMMAND_GET_VOLUME = 10
        const val COMMAND_SET_VOLUME = 11
        const val COMMAND_CHANGE_PLAYER_STATE = 12
        const val COMMAND_SET_REPEAT_MODE = 13
        const val COMMAND_SET_SHUFFLE_MODE = 14
        const val COMMAND_SET_PLAYBACK_SPEED = 15
        const val COMMAND_SEEK_IN_CURRENT_MEDIA_ITEM = 16
        const val COMMAND_SEEK_TO_MEDIA_ITEM = 17
    }

    /** 當播放器處於 [STATE_READY] 時，是否應自動播放。 */
    var playWhenReady: Boolean

    /** 目前播放狀態，取值為 STATE_ 之一。 */
    val playbackState: Int

    /** 影片資訊 */
    val videoSize: VideoSize

    /** 縮放模式 */
    var resizeMode: Int

    /** 目前是否正在播放。 */
    val isPlaying: Boolean

    /** 目前播放位置（毫秒）。 */
    val currentPosition: Long

    /** 媒體總長度（毫秒）。 */
    val duration: Long

    /** 緩衝位置（毫秒）。 */
    val bufferedPosition: Long

    /** 目前播放清單 */
    val currentTimeline: Timeline

    /** 目前播放項目的索引 */
    val currentMediaItemIndex: Int

    /** 目前週期索引 */
    val currentPeriodIndex: Int

    /** 目前軌道資訊 */
    val currentTracks: Tracks

    var repeatMode: Int
    var shuffleModeEnabled: Boolean
    var volume: Float

    val seekBackIncrement: Long
    val seekForwardIncrement: Long

    fun isCommandAvailable(command: Int): Boolean

    fun prepare()
    fun play()
    fun pause()
    fun stop()
    fun release()
    fun seekTo(positionMs: Long)
    fun seekToNext()
    fun seekToPrevious()
    fun seekBack()
    fun seekForward()
    fun hasNext(): Boolean
    fun hasPrevious(): Boolean
    fun setMediaItem(mediaItem: MediaItem)
    fun setMediaItems(mediaItems: List<MediaItem>)
    fun setMediaItem(mediaItem: MediaItem, startPositionMs: Long)
    fun setMediaItem(mediaItem: MediaItem, resetPosition: Boolean)

    /** 設置影像渲染表面。 */
    fun setVideoSurface(surface: Any?)

    /** 獲取播放參數 */
    val playbackParameters: PlaybackParameters
    /** 設置播放參數 */
    fun setPlaybackParameters(playbackParameters: PlaybackParameters)

    /** 事件監聽器介面。 */
    interface Listener {
        fun onPlaybackStateChanged(state: Int) {}
        fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {}
        fun onIsPlayingChanged(isPlaying: Boolean) {}
        fun onPositionDiscontinuity(reason: Int) {}
        fun onPlayerError(error: PlaybackException) {}
        fun onVideoSizeChanged(videoSize: VideoSize) {}
        fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
        fun onRepeatModeChanged(repeatMode: Int) {}
        fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
        fun onVolumeChanged(volume: Float) {}
        fun onTimelineChanged(timeline: Timeline, reason: Int) {}
        fun onEvents(player: Player, events: Events) {}
    }

    /** 簡單的事件集合物件。 */
    class Events(private val flags: Set<Int>) {
        fun contains(event: Int): Boolean = flags.contains(event)
    }

    /** Event types for [Listener.onEvents]. */
    annotation class Event

    fun addListener(listener: Listener)
    fun removeListener(listener: Listener)
}
