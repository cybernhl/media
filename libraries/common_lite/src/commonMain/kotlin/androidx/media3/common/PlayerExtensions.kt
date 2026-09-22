package androidx.media3.common

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest

/**
 * Extension to observe player events as a coroutine.
 */
suspend fun Player.listen(action: suspend (Player.Events) -> Unit) {
    callbackFlow {
        val listener = object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                trySend(events)
            }
        }
        addListener(listener)
        awaitClose {
            removeListener(listener)
        }
    }.collectLatest {
        action(it)
    }
}

/**
 * Extension to observe specific player events as a coroutine.
 */
suspend fun Player.listenTo(vararg events: Int, action: suspend (Player.Events) -> Unit) {
    callbackFlow {
        val listener = object : Player.Listener {
            override fun onEvents(player: Player, playerEvents: Player.Events) {
                if (events.any { playerEvents.contains(it) }) {
                    trySend(playerEvents)
                }
            }
        }
        addListener(listener)
        awaitClose {
            removeListener(listener)
        }
    }.collectLatest {
        action(it)
    }
}

/**
 * Mutes the player.
 */
fun Player.mute() {
    volume = 0f
}

/**
 * Unmutes the player.
 */
fun Player.unmute() {
    volume = 1f
}
