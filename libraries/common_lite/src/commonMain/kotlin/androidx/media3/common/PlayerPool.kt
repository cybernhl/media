/*
 * Copyright 2023 The Android Open Source Project
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
import kotlinx.coroutines.channels.Channel

/**
 * A pool of [Player] instances in Pure Kotlin KMP.
 */
@UnstableApi
public class PlayerPool<T : Player>(
    private val poolCapacity: Int,
    private val playerFactory: () -> T
) {

    init {
        require(poolCapacity > 0) { "poolCapacity must be greater than 0" }
    }

    private val availablePlayers = Channel<T>(Channel.UNLIMITED)
    private val allPlayers = mutableListOf<T>()
    private val activePlayers = mutableSetOf<T>()
    private var isReleased = false

    public suspend fun acquire(): T {
        check(!isReleased) { "PlayerPool is already released" }
        availablePlayers.tryReceive().getOrNull()?.let {
            activePlayers.add(it)
            return it
        }

        if (allPlayers.size < poolCapacity) {
            val player = playerFactory()
            allPlayers.add(player)
            activePlayers.add(player)
            return player
        }
        val player = availablePlayers.receive()
        activePlayers.add(player)
        return player
    }

    public fun executeForAll(action: T.() -> Unit) {
        allPlayers.forEach { it.action() }
    }

    public fun executeForAcquired(action: T.() -> Unit) {
        activePlayers.forEach { it.action() }
    }

    public fun yield(player: T) {
        if (activePlayers.remove(player)) {
            player.playWhenReady = false
            player.stop()
            availablePlayers.trySend(player)
        }
    }

    public fun release() {
        isReleased = true
        allPlayers.forEach(Player::release)
        allPlayers.clear()
        activePlayers.clear()
        availablePlayers.cancel()
    }
}
