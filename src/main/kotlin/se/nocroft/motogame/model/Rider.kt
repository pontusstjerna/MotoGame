package se.nocroft.motogame.model

import com.badlogic.gdx.math.Vector2

class Rider {
    // height in meters
    val height = 1.25f
    val crotchY = .7f

    var leaningForward = false
    private set

    var leaningBackward = false

    fun leanForward() {
        leaningBackward = false
        leaningForward = true
    }

    fun leanBackward() {
        leaningBackward = false
        leaningBackward = true
    }

    fun stopLeaning() {
        leaningBackward = false
        leaningForward = false
    }
}