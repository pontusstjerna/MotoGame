package se.nocroft.motogame.screen

interface GameService {
    val highscore: Int
    fun reset()
    val isDead: Boolean
    val distance: Int
}