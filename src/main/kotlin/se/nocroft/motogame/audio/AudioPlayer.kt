package se.nocroft.motogame.audio

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import se.nocroft.motogame.GameEvent.*
import se.nocroft.motogame.MUSIC_INTRO_LOOP_THRESHOLD
import se.nocroft.motogame.model.GameWorld
import se.nocroft.motogame.screen.GameService
import kotlin.math.absoluteValue
import kotlin.random.Random

class AudioPlayer(private val gameService: GameService) {

    val BASE_PATH_SFX = "assets/sound/sfx"
    val BASE_PATH_MUSIC = "assets/sound/music"


    // Loads the sound into memory first when it is required
    private val engineLoop by lazy {
        Sound(Gdx.files.local("$BASE_PATH_SFX/engine_loop_4.wav"))
    }

    private val impactLight by lazy {
        (1..3).map {
            Sound(Gdx.files.local("$BASE_PATH_SFX/impact_light_$it.wav"))
        }.toTypedArray()
    }

    private val impactMid by lazy {
        (1..3).map {
            Sound(Gdx.files.local("$BASE_PATH_SFX/impact_mid_$it.wav"))
        }.toTypedArray()
    }

    private val end by lazy {
        Sound(Gdx.files.local("$BASE_PATH_SFX/end.wav"))
    }

    private val music by lazy {
        Gdx.audio.newMusic(Gdx.files.local("$BASE_PATH_MUSIC/motogame_music_full.wav")).apply {
            // Maybe we should have a setting for music volume?
            volume = 0.9f
        }
    }

    private val musicIntroLoop =
        Gdx.audio.newMusic(Gdx.files.local("$BASE_PATH_MUSIC/motoGame_music_introloop.wav")).apply{
           setOnCompletionListener { musicIntroLoop ->
                musicIntroLoop.stop()
                musicMain.play()
                musicMain.isLooping = true
            }
            volume = 0.9f
        }


    private val musicIntro =
        Gdx.audio.newMusic(Gdx.files.local("$BASE_PATH_MUSIC/motoGame_music_IntroOneShot.wav")).apply {
           setOnCompletionListener {
                musicIntroLoop.play()
                musicIntroLoop.isLooping = true
            }
            volume = 0.9f
        }


    private val musicMain =
        Gdx.audio.newMusic(Gdx.files.local("$BASE_PATH_MUSIC/motoGame_music_MainLoop.wav")).apply{
            volume = 0.9f
        }


    /*
        Beats-per-minute: 108 BPM
        Beats-per-second: 1.8 Hz
        Length of 1 beat: 0.5556 second = 556 msec
        Length of 1 bar (4 beats): 2.2222 second
     */


    private var enginePitch = 1f

    // TODO RESUME and START are called at the same time when starting game, RESUME is called after DIE, maybe it should be START instead?
    // TODO seems like DIE is different amount of times in one frame every death
    // TODO RESET is never called, when using reset option in menu RESUME is called instead
    // TODO end maybe should be played one instance at a time?

    init {
        gameService.addGameEventListener {
            when (it) {
                PAUSE -> {
                    engineLoop.stop()
                    musicIntro.pause()

                    musicIntroLoop.pause()
                    musicMain.pause()
                }
                RESUME -> {
                    if (musicMain.isLooping)  musicMain.play() else musicIntroLoop.play()
                    engineLoop.playLoop()
                }
                DIE -> {
                    engineLoop.stop()
                    musicIntro.stop()

                    musicIntroLoop.stop()
                    musicIntroLoop.isLooping = false;

                    musicMain.stop()
                    musicMain.isLooping = false;
                    end.play()

                }
                START -> {
                    // TODO if START is fixed, play these at start, otherwise doubles are played on top of each other
                    //engineLoop.playLoop()
                    //musicIntro.play()
                }
                QUIT -> {
                    musicIntro.stop()
                    musicIntroLoop.stop()
                    musicMain.stop()
                }
                RESET -> {
                    if (musicMain.isLooping)  {
                        musicMain.stop()
                        musicMain.isLooping = false
                    } else if (musicIntroLoop.isLooping) {
                        musicIntroLoop.stop()
                        musicIntroLoop.isLooping = false
                    } else {
                        musicIntro.stop()
                    }
                    /*music.stop()
                    music.play()*/
                }

            }
        }
    }

    fun update(world: GameWorld) {
        val bike = world.bike
        val goal = 0.8f + (bike.wheelThrust / bike.maxThrust) * 0.7f
        val pitchChangeSpeed = 0.1f
        enginePitch += (goal - enginePitch) * pitchChangeSpeed
        engineLoop.setPitch(enginePitch.absoluteValue)

        musicIntroLoop.isLooping = gameService.distance < MUSIC_INTRO_LOOP_THRESHOLD

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
        musicIntro.dispose()
        musicIntroLoop.dispose()
        musicMain.dispose()
    }
}
