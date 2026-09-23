/*
 * Copyright (C) 2020 The Android Open Source Project
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
 * Player interface definition in Pure Kotlin KMP.
 * Aligned with androidx.media3.common.Player.
 */
public interface Player {

    @Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
    public annotation class RepeatMode

    @Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
    public annotation class Event

    @Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
    public annotation class Command

    public class Commands(private val flags: Set<Int>) {
        public fun contains(@Command command: Int): Boolean = flags.contains(command)
    }

    public class Events(private val flags: Set<Int>) {
        public fun contains(@Event event: Int): Boolean = flags.contains(event)
        public fun containsAny(vararg events: Int): Boolean = events.any { flags.contains(it) }
    }

    companion object {
        public const val STATE_IDLE: Int = 1
        public const val STATE_BUFFERING: Int = 2
        public const val STATE_READY: Int = 3
        public const val STATE_ENDED: Int = 4

        public const val REPEAT_MODE_OFF: Int = 0
        public const val REPEAT_MODE_ONE: Int = 1
        public const val REPEAT_MODE_ALL: Int = 2

        // Events
        public const val EVENT_TIMELINE_CHANGED: Int = 0
        public const val EVENT_MEDIA_ITEM_TRANSITION: Int = 1
        public const val EVENT_TRACKS_CHANGED: Int = 2
        public const val EVENT_IS_LOADING_CHANGED: Int = 3
        public const val EVENT_PLAYBACK_STATE_CHANGED: Int = 4
        public const val EVENT_PLAY_WHEN_READY_CHANGED: Int = 5
        public const val EVENT_PLAYBACK_SUPPRESSION_REASON_CHANGED: Int = 6
        public const val EVENT_IS_PLAYING_CHANGED: Int = 7
        public const val EVENT_REPEAT_MODE_CHANGED: Int = 8
        public const val EVENT_SHUFFLE_MODE_ENABLED_CHANGED: Int = 9
        public const val EVENT_PLAYER_ERROR: Int = 10
        public const val EVENT_POSITION_DISCONTINUITY: Int = 11
        public const val EVENT_PLAYBACK_PARAMETERS_CHANGED: Int = 12
        public const val EVENT_AVAILABLE_COMMANDS_CHANGED: Int = 13
        public const val EVENT_SEEK_BACK_INCREMENT_CHANGED: Int = 14
        public const val EVENT_SEEK_FORWARD_INCREMENT_CHANGED: Int = 15
        public const val EVENT_MEDIA_METADATA_CHANGED: Int = 16
        public const val EVENT_PLAYLIST_METADATA_CHANGED: Int = 17
        public const val EVENT_VOLUME_CHANGED: Int = 18
        public const val EVENT_VIDEO_SIZE_CHANGED: Int = 19
        public const val EVENT_RENDERED_FIRST_FRAME: Int = 20
        public const val EVENT_METADATA: Int = 28

        // Commands
        public const val COMMAND_PLAY_PAUSE: Int = 1
        public const val COMMAND_PREPARE: Int = 2
        public const val COMMAND_STOP: Int = 3
        public const val COMMAND_SEEK_IN_CURRENT_MEDIA_ITEM: Int = 4
        public const val COMMAND_SEEK_TO_DEFAULT_POSITION: Int = 5
        public const val COMMAND_SEEK_TO_MEDIA_ITEM: Int = 6
        public const val COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM: Int = 7
        public const val COMMAND_SEEK_TO_PREVIOUS: Int = 8
        public const val COMMAND_SEEK_TO_NEXT_MEDIA_ITEM: Int = 9
        public const val COMMAND_SEEK_TO_NEXT: Int = 10
        public const val COMMAND_SEEK_BACK: Int = 11
        public const val COMMAND_SEEK_FORWARD: Int = 12
        public const val COMMAND_SET_SPEED_AND_PITCH: Int = 13
        public const val COMMAND_SET_PLAYBACK_SPEED: Int = 13
        public const val COMMAND_SET_REPEAT_MODE: Int = 14
        public const val COMMAND_SET_SHUFFLE_MODE: Int = 15
        public const val COMMAND_CHANGE_MEDIA_ITEMS: Int = 16
        public const val COMMAND_GET_CURRENT_MEDIA_ITEM: Int = 17
        public const val COMMAND_GET_TIMELINE: Int = 18
        public const val COMMAND_GET_METADATA: Int = 19
        public const val COMMAND_SET_VIDEO_SURFACE: Int = 20
        public const val COMMAND_GET_VOLUME: Int = 21
        public const val COMMAND_SET_VOLUME: Int = 22
        public const val COMMAND_GET_TRACKS: Int = 23
        public const val COMMAND_CHANGE_PLAYER_STATE: Int = 24
    }

    public var playWhenReady: Boolean
    public val playbackState: Int
    public val isPlaying: Boolean
    public val playerError: PlaybackException?
    public val videoSize: VideoSize
    public var resizeMode: Int

    public val currentPosition: Long
    public val duration: Long
    public val bufferedPosition: Long

    public val currentTimeline: Timeline
    public val currentMediaItem: MediaItem?
    public val mediaMetadata: MediaMetadata
    public val playlistMetadata: MediaMetadata
    public val currentMediaItemIndex: Int
    public val currentPeriodIndex: Int
    public val currentTracks: Tracks
    public val isCurrentMediaItemLive: Boolean
    public val isPlayingAd: Boolean

    public var repeatMode: Int
    public var shuffleModeEnabled: Boolean
    public var volume: Float

    public val seekBackIncrement: Long
    public val seekForwardIncrement: Long

    public fun isCommandAvailable(@Command command: Int): Boolean
    public fun getAvailableCommands(): Commands

    public fun prepare()
    public fun play()
    public fun pause()
    public fun stop()
    public fun release()
    public fun seekTo(positionMs: Long)
    public fun seekTo(mediaItemIndex: Int, positionMs: Long)
    public fun seekToDefaultPosition()
    public fun seekToDefaultPosition(mediaItemIndex: Int)
    public fun seekToNext()
    public fun seekToPrevious()
    public fun seekBack()
    public fun seekForward()
    public fun hasNext(): Boolean
    public fun hasPrevious(): Boolean

    public fun setMediaItem(mediaItem: MediaItem)
    public fun setMediaItems(mediaItems: List<MediaItem>)
    public fun setMediaItem(mediaItem: MediaItem, startPositionMs: Long)
    public fun setMediaItem(mediaItem: MediaItem, resetPosition: Boolean)

    public fun setVideoSurface(surface: Any?)
    public fun setVideoSurfaceView(surfaceView: Any?)
    public fun clearVideoSurfaceView(surfaceView: Any?)
    public fun setVideoTextureView(textureView: Any?)
    public fun clearVideoTextureView(textureView: Any?)

    public var playbackParameters: PlaybackParameters

    public interface Listener {
        public fun onPlaybackStateChanged(state: Int) {}
        public fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {}
        public fun onIsPlayingChanged(isPlaying: Boolean) {}
        public fun onPositionDiscontinuity(reason: Int) {}
        public fun onPlayerError(error: PlaybackException) {}
        public fun onVideoSizeChanged(videoSize: VideoSize) {}
        public fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
        public fun onRepeatModeChanged(@RepeatMode repeatMode: Int) {}
        public fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
        public fun onVolumeChanged(volume: Float) {}
        public fun onTimelineChanged(timeline: Timeline, reason: Int) {}
        public fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {}
        public fun onPlaylistMetadataChanged(playlistMetadata: MediaMetadata) {}
        public fun onEvents(player: Player, events: Events) {}
    }

    public fun addListener(listener: Listener)
    public fun removeListener(listener: Listener)
}
