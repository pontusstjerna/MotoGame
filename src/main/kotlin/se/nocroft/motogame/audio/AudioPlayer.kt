package se.nocroft.motogame.audio

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import org.lwjgl.input.Keyboard
import se.nocroft.motogame.GameEvent.*
import se.nocroft.motogame.model.Bike
import se.nocroft.motogame.screen.GameService
import kotlin.math.absoluteValue

class AudioPlayer(private val gameService: GameService) {

    val BASE_PATH = "assets/sound/SFX"

    // Loads the sound into memory first when it is required
    private val engineLoop by lazy {
        Sound(Gdx.files.local("$BASE_PATH/engine_loop_4.wav"))
    }

    private val impactLight1 by lazy {
        Sound(Gdx.files.local("$BASE_PATH/impact_light_1.wav"))
    }
    private val impactLight2 by lazy {
        Sound(Gdx.files.local("$BASE_PATH/impact_light_2.wav"))
    }
    private val impactLight3 by lazy {
        Sound(Gdx.files.local("$BASE_PATH/impact_light_3.wav"))
    }

    private val impactMid1 by lazy {
        Sound(Gdx.files.local("$BASE_PATH/impact_mid_1.wav"))
    }
    private val impactMid2 by lazy {
        Sound(Gdx.files.local("$BASE_PATH/impact_mid_2.wav"))
    }
    private val impactMid3 by lazy {
        Sound(Gdx.files.local("$BASE_PATH/impact_mid_3.wav"))
    }


    private val end by lazy {
        Sound(Gdx.files.local("$BASE_PATH/end.wav"))
    }

    private val music by lazy {
        Gdx.audio.newMusic(Gdx.files.local("assets/sound/music/motoGame_music_full.wav")).apply {
            // Maybe we should have a setting for music volume?
            volume = 0.9f
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
                    end.play()
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

        // Testing randomizing sounds
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            when(val i : Int = (Math.random() * 3 + 1).toInt()){
                1 -> impactMid1.play()
                2 -> impactMid2.play()
                3 -> impactMid3.play()
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            when(val i : Int = (Math.random() * 3 + 1).toInt()){
                1 -> impactLight1.play()
                2 -> impactLight2.play()
                3 -> impactLight3.play()
            }
        }
    }

    // Clears audio files from memory
    fun dispose() {
        engineLoop.dispose()
        impactLight1.dispose()
        impactLight2.dispose()
        impactLight3.dispose()
        impactMid1.dispose()
        impactMid2.dispose()
        impactMid3.dispose()
        end.dispose()
    }
}