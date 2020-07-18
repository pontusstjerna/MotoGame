package se.nocroft.motogame.renderer

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import se.nocroft.motogame.model.Rider
import se.nocroft.motogame.renderer.RiderTexture.State.*
import kotlin.math.max
import kotlin.math.min

class RiderTexture(file: FileHandle?, useMipMaps: Boolean) : Texture(file, useMipMaps) {

    enum class State {
        NEUTRAL,
        NEUTRAL_TO_BACK,
        BACK_TO_NEUTRAL,
        NEUTRAL_TO_FORWARD,
        FORWARD_TO_NEUTRAL,
    }

    val frameWidth: Int = 150
    val frameHeight: Int = 136

    private var frame = 0
    private var frameTimer = 0f
    private val NUM_FRAMES = 9
    private val MS_PER_FRAME = .015f
    var state: State? = null

    fun update(rider: Rider, delta: Float) {
        frameTimer += delta
        if (frameTimer > MS_PER_FRAME) {
            frameTimer = 0f
            state = getState(rider)
            updateFrames()
        }
    }

    fun getOffsetX(): Int {
        return frame * frameWidth
    }

    fun getOffsetY(): Int {
        return when(state) {
            NEUTRAL_TO_FORWARD, FORWARD_TO_NEUTRAL -> frameHeight
            else -> 0
        }
    }

    private fun updateFrames() {
        if (state == NEUTRAL_TO_FORWARD || state == NEUTRAL_TO_BACK) {
            frame = min(frame + 1, NUM_FRAMES - 1)
        } else if (state == FORWARD_TO_NEUTRAL || state == BACK_TO_NEUTRAL) {
            frame = max(frame - 1, 0)
        }

        if (frame == 0) {
            state = NEUTRAL
        }
    }

    private fun getState(rider: Rider): State {
        if (rider.leaningForward) {
            return when (state) {
                BACK_TO_NEUTRAL, NEUTRAL_TO_BACK -> BACK_TO_NEUTRAL
                else -> NEUTRAL_TO_FORWARD
            }
        }

        if (rider.leaningBackward) {
            return when (state) {
                NEUTRAL_TO_FORWARD, FORWARD_TO_NEUTRAL -> FORWARD_TO_NEUTRAL
                else -> NEUTRAL_TO_BACK
            }
        }

        return when (state) {
            NEUTRAL_TO_BACK, BACK_TO_NEUTRAL -> BACK_TO_NEUTRAL
            NEUTRAL_TO_FORWARD, FORWARD_TO_NEUTRAL -> FORWARD_TO_NEUTRAL
            else -> NEUTRAL
        }
    }
}