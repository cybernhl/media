package androidx.media3.common.util

import androidx.media3.common.Player

object Util {
    fun shouldEnablePlayPauseButton(player: Player?): Boolean {
        return player != null && player.isCommandAvailable(Player.COMMAND_PLAY_PAUSE)
    }

    fun shouldShowPlayButton(player: Player?): Boolean {
        if (player == null) return true
        return !player.playWhenReady || player.playbackState == Player.STATE_ENDED || player.playbackState == Player.STATE_IDLE
    }

    fun handlePlayPauseButtonAction(player: Player?) {
        if (player == null) return
        val playbackState = player.playbackState
        if (playbackState == Player.STATE_IDLE) {
            player.prepare()
            player.play()
        } else if (playbackState == Player.STATE_ENDED) {
            player.seekTo(0)
            player.play()
        } else if (player.playWhenReady) {
            player.pause()
        } else {
            player.play()
        }
    }
}
