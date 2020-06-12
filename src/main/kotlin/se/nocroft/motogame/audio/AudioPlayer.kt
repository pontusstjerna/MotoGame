package se.nocroft.motogame.audio

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import se.nocroft.motogame.GameEvent.*
import se.nocroft.motogame.model.GameWorld
import se.nocroft.motogame.screen.GameService
import kotlin.math.absoluteValue
import kotlin.random.Random

class AudioPlayer(private val gameService: GameService) {

    val BASE_PATH = "assets/sound/SFX"

    // Loads the sound into memory first when it is required
    private val engineLoop by lazy {
        Sound(Gdx.files.local("$BASE_PATH/engine_loop_4.wav"))
    }

    private val impactLight by lazy {
        (1..3).map {
            Sound(Gdx.files.local("$BASE_PATH/impact_light_$it.wav"))
        }.toTypedArray()
    }

    private val impactMid by lazy {
        (1..3).map {
            Sound(Gdx.files.local("$BASE_PATH/impact_mid_$it.wav"))
        }.toTypedArray()
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

    private val musicIntro by lazy {
        Gdx.audio.newMusic(Gdx.files.local("assets/sound/music/motoGame_music_introloop.wav")).apply{
            volume = 0.9f
        }
    }

    private val musicIntroOneShot by lazy {
        Gdx.audio.newMusic(Gdx.files.local("assets/sound/music/motoGame_music_IntroOneShot.wav")).apply{
            volume = 0.9f
        }
    }

    private val musicMain by lazy {
        Gdx.audio.newMusic(Gdx.files.local("assets/sound/music/motoGame_music_MainLoop.wav")).apply{
            volume = 0.9f
        }
    }

    /*
        Beats-per-minute: 108 BPM
        Beats-per-second: 1.8 Hz
        Length of 1 beat: 0.5556 second = 556 msec
        Length of 1 bar (4 beats): 2.2222 second
     */


    private var enginePitch = 1f

    init {
        gameService.addGameEventListener {
            when (it) {
                PAUSE -> {
                    engineLoop.stop()
                    musicIntro.pause()
                }
                RESUME -> {
                    musicIntroOneShot.play()
                    engineLoop.playLoop()
                }
                DIE -> {
                    engineLoop.stop()
                    musicIntro.stop()
                    end.play()
                }
                START -> {
                    engineLoop.playLoop()
                    musicIntroOneShot.play()

                }
                QUIT -> music.stop()
                RESET -> {
                    // TODO isn't used, music should reset when game resets
                    /*music.stop()
                    music.play()*/
                }

            }
        }
    }
    var introLoopCheck = true;

    fun update(world: GameWorld) {
        val bike = world.bike
        val goal = 0.8f + (bike.wheelThrust / bike.maxThrust) * 0.7f
        val pitchChangeSpeed = 0.1f
        enginePitch += (goal - enginePitch) * pitchChangeSpeed
        engineLoop.setPitch(enginePitch.absoluteValue)



        if(world.distance > 3f){
            introLoopCheck = false
        }
        musicIntroOneShot.setOnCompletionListener() {
            musicIntro.play()
            musicIntro.isLooping = true
        }
        if(!introLoopCheck){
            musicIntro.isLooping = false
            musicIntro.setOnCompletionListener {
                musicIntro.stop()
                musicMain.play()
                musicMain.isLooping = true
            }
        }


        // Testing randomizing sounds
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            impactLight[Random.nextInt(0, 2)].play()
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            impactMid[Random.nextInt(0, 2)].play()
        }
    }

    // Clears audio files from memory
    fun dispose() {
        engineLoop.dispose()
        impactLight.forEach { it.dispose() }
        impactMid.forEach { it.dispose() }
        end.dispose()
    }
}