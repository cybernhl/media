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
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Continuously listens to the [Listener.onEvents] callback, passing the received
 * [Events] to the provided [onEvents] function.
 *
 * This function can be called from any thread. The [onEvents] function will be invoked on the
 * thread associated with the player's event dispatch.
 *
 * If, during the execution of [onEvents], an exception is thrown, the coroutine corresponding to
 * listening to the Player will be terminated. Any used resources will be cleaned up (e.g. removing
 * of the listeners) and exception will be re-thrown right after the last suspension point.
 *
 * @param onEvents The function to handle player events.
 * @return Nothing This function never returns normally. It will either continue indefinitely or
 *   terminate due to an exception or cancellation.
 */
@UnstableApi
suspend fun Player.listen(onEvents: Player.(Player.Events) -> Unit): Nothing {
    listenImpl(null, onEvents)
}

/**
 * Continuously listens to the [Listener.onEvents] callback, passing the received
 * [Events] to the provided [onEvents] function. A non-zero number of events has to be
 * specified (hence [firstEvent] and [otherEvents]). The order is not important.
 *
 * This function can be called from any thread. The [onEvents] function will be invoked on the
 * thread associated with the player's event dispatch.
 *
 * If, during the execution of [onEvents], an exception is thrown, the coroutine corresponding to
 * listening to the Player will be terminated. Any used resources will be cleaned up (e.g. removing
 * of the listeners) and exception will be re-thrown right after the last suspension point.
 *
 * @param firstEvent One of the [events][Event] to listen to. Does not have to actually be
 *   the first one, since the order is not taken into account. This parameter is separated from
 *   [otherEvents] to avoid creating an array when only one event is needed or avoid accidentally
 *   not passing any events.
 * @param otherEvents The set of other [events][Event] to listen for.
 * @param onEvents The function to handle player events.
 * @return Nothing This function never returns normally. It will either continue indefinitely or
 *   terminate due to an exception or cancellation.
 */
@UnstableApi
suspend fun Player.listenTo(
    @Player.Event firstEvent: Int,
    vararg otherEvents: @Player.Event Int,
    onEvents: Player.(Player.Events) -> Unit,
): Nothing {
    val eventArray = intArrayOf(firstEvent, *otherEvents)
    listenImpl(eventArray, onEvents)
}

/**
 * Implements the core listening logic for [Events] in a KMP-compatible way,
 * aligning with official Media3 binary signatures, avoiding top-level private class
 * bytecode/package pollution, and ensuring full cross-platform compatibility across all KMP targets.
 */
private suspend fun Player.listenImpl(
    filterEvents: IntArray?,
    onEvents: Player.(Player.Events) -> Unit,
): Nothing {
    suspendCancellableCoroutine<Nothing> { continuation ->
        var isCancelled = false
        val listener = object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                if (isCancelled) return
                try {
                    if (filterEvents == null || filterEvents.isEmpty() || filterEvents.any { events.contains(it) }) {
                        player.onEvents(events)
                    }
                } catch (t: Throwable) {
                    isCancelled = true
                    if (continuation.isActive) {
                        continuation.resumeWithException(t)
                    }
                }
            }
        }
        addListener(listener)
        continuation.invokeOnCancellation {
            isCancelled = true
            removeListener(listener)
        }
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
