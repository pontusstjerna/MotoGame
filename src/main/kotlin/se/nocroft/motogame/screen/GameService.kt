package se.nocroft.motogame.screen

interface GameService {
    val highscore: Int
    fun reset()
    fun pause()
    fun exitToMenu()
    fun resume()
    val isPaused: Boolean
    val isDead: Boolean
    val distance: Int
}