package se.nocroft.motogame.model

interface WorldService {
    fun reset()
    val isDead: Boolean
    val distance: Float
}