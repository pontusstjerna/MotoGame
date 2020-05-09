package se.nocroft.motogame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import ktx.app.KtxScreen
import se.nocroft.motogame.audio.AudioPlayer
import se.nocroft.motogame.model.GameWorld
import se.nocroft.motogame.renderer.GameRenderer
import se.nocroft.motogame.renderer.ui.UIRenderer
import kotlin.properties.Delegates

// https://github.com/Quillraven/SimpleKtxGame/blob/01-app/core/src/com/libktx/game/screen/GameScreen.kt

class GameScreen(private val menuService: MenuService) : KtxScreen, GameService {

    private var pauseListeners = mutableListOf<() -> Unit>()
    private var resumeListeners = mutableListOf<() -> Unit>()

    override var isPaused: Boolean by Delegates.observable(false) { _, _, newValue ->
        if (newValue) {
            pauseListeners.forEach { it() }
        } else {
            resumeListeners.forEach { it() }
        }
    }
        private set

    override var highscore: Int = Gdx.app.getPreferences("motogame").getInteger("highscore")
        private set

    override val isDead: Boolean
        get() = world.isDead

    override val distance: Int
        get() = world.distance.toInt()

    private val world: GameWorld = GameWorld()

    private val gameRenderer = GameRenderer(world, this)
    private val uiRenderer = UIRenderer(this)
    private val audioPlayer = AudioPlayer(this)

    private var accumulator: Float = 0.0f

    override fun show() {
        reset()
        Gdx.input.inputProcessor = uiRenderer.stage
        super.show()
    }

    override fun render(delta: Float) {
        gameRenderer.render(delta)
        uiRenderer.render(delta)
        audioPlayer.update(world.bike)
        if (!world.isDead && !isPaused) {
            checkInput()
            updatePhysics(delta)
        }
    }

    override fun dispose() {
        gameRenderer.dispose()
        uiRenderer.dispose()
        super.dispose()
    }

    override fun resize(width: Int, height: Int) {
        gameRenderer.resize(width, height)
        uiRenderer.resize(width, height)
        super.resize(width, height)
    }

    override fun reset() {
        updateHighscore()
        world.reset()
        resume()
    }

    override fun pause() {
        isPaused = true
    }

    override fun exitToMenu() {
        menuService.goToMenu()
    }

    override fun resume() {
        isPaused = false
    }

    override fun addPauseListener(action: () -> Unit) {
        pauseListeners.add(action)
    }

    override fun addResumeListener(action: () -> Unit) {
        resumeListeners.add(action)
    }

    private fun checkInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            world.bike.brake()
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            world.bike.accelerate()
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            world.bike.leanForward()
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            world.bike.leanBack()
        }
    }

    private fun updatePhysics(delta: Float) {
        accumulator += delta

        while (accumulator >= world.timeStep) {
            world.update()
            accumulator -= world.timeStep
        }
    }

    private fun updateHighscore() {
        val prefs = Gdx.app.getPreferences("motogame")
        val score = world.distance.toInt()
        if (score > highscore) {
            prefs.putInteger("highscore", score)
            highscore = score
            prefs.flush()
        }
    }
}