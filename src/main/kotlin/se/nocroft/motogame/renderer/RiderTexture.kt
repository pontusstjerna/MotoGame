package se.nocroft.motogame.renderer

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import se.nocroft.motogame.model.Rider

class RiderTexture(file: FileHandle?, useMipMaps: Boolean) : Texture(file, useMipMaps) {

    enum class State {
        NEUTRAL,
        FORWARD,
        BACKWARD
    }

    val frameWidth: Int = 150
    val offsetsX: Map<State, Int> = mapOf(
            State.NEUTRAL to 0,
            State.BACKWARD to frameWidth,
            State.FORWARD to frameWidth * 2
    )

    fun getStateFromModel(rider: Rider): State {
        if (rider.leaningBackward) {
            return State.BACKWARD
        }

        if (rider.leaningForward) {
            return State.FORWARD
        }

        return State.NEUTRAL
    }

    fun getOffsetXFromModel(rider: Rider): Int {
        return offsetsX.getOrElse(getStateFromModel(rider)) { 0 }
    }

}