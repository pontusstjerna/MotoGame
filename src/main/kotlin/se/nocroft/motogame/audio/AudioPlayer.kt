package se.nocroft.motogame.audio

import com.badlogic.gdx.Gdx
import se.nocroft.motogame.model.Bike
import se.nocroft.motogame.screen.GameService

class AudioPlayer(private val gameService: GameService) {

    val BASE_PATH = "assets/sound/SFX"

    // Loads the sound into memory first when it is required
    private val engineLoop by lazy {
        Sound(Gdx.files.local("$BASE_PATH/engine_loop_1.wav"))
    }

    private val impactLight by lazy {
        Sound(Gdx.files.local("$BASE_PATH/impact_light_1.wav"))
    }

    init {
        /*gameService.addPauseListener {
            engineLoop.stop()
        }

        gameService.addResumeListener {
            engineLoop.playLoop()
        }*/

        engineLoop.playLoop()
    }

    fun update(bike: Bike) {
        engineLoop.setPitch(1 + bike.thrust / bike.maxThrust)
    }

    // Clears audio files from memory
    fun dispose() {
        engineLoop.dispose()
        impactLight.dispose()
    }
}