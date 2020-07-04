package se.nocroft.motogame.model

class Rider {
    // height in meters
    val height = 1.8f
    val crotchY = 1f

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