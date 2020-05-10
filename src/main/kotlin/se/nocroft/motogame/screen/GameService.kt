package se.nocroft.motogame.screen

import se.nocroft.motogame.GameEvent

interface GameService {
    val highscore: Int
    fun reset()
    fun pause()
    fun exitToMenu()
    fun resume()
    fun addGameEventListener(action: (GameEvent) -> Unit)
    val isPaused: Boolean
    val isDead: Boolean
    val distance: Int
}