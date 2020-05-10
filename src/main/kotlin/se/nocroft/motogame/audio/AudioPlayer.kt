package se.nocroft.motogame.audio

import com.badlogic.gdx.Gdx
import se.nocroft.motogame.GameEvent.*
import se.nocroft.motogame.model.Bike
import se.nocroft.motogame.screen.GameService
import kotlin.math.absoluteValue

class AudioPlayer(private val gameService: GameService) {

    val BASE_PATH = "assets/sound/SFX"

    // Loads the sound into memory first when it is required
    private val engineLoop by lazy {
        Sound(Gdx.files.local("$BASE_PATH/engine_loop_1.wav"))
    }

    private val impactLight by lazy {
        Sound(Gdx.files.local("$BASE_PATH/impact_light_1.wav"))
    }

    private val music by lazy {
        Gdx.audio.newMusic(Gdx.files.local("assets/sound/music.mp3")).apply {
            // Maybe we should have a setting for music volume?
            volume = 0.4f
        }
    }

    private var enginePitch = 1f

    init {
        gameService.addGameEventListener {
            when (it) {
                PAUSE -> {
                    engineLoop.stop()
                    music.pause()
                }
                RESUME -> {
                    music.play()
                    engineLoop.playLoop()
                }
                DIE -> {
                    engineLoop.stop()
                    music.stop()
                    impactLight.play()
                }
                START -> {
                    engineLoop.playLoop()
                    music.play()
                    music.isLooping = true
                }
                QUIT -> music.stop()
                RESET -> {}
            }
        }
    }

    fun update(bike: Bike) {
        val goal = 0.8f + (bike.wheelThrust / bike.maxThrust) * 0.7f
        val pitchChangeSpeed = 0.1f
        enginePitch += (goal - enginePitch) * pitchChangeSpeed
        engineLoop.setPitch(enginePitch.absoluteValue)
    }

    // Clears audio files from memory
    fun dispose() {
        engineLoop.dispose()
        impactLight.dispose()
    }
}