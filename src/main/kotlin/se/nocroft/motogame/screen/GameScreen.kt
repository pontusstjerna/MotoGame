package se.nocroft.motogame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Preferences
import ktx.app.KtxScreen
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.model.GameWorld
import se.nocroft.motogame.renderer.GameRenderer
import se.nocroft.motogame.renderer.ui.UIRenderer
import kotlin.math.max

// https://github.com/Quillraven/SimpleKtxGame/blob/01-app/core/src/com/libktx/game/screen/GameScreen.kt

class GameScreen : KtxScreen, GameService {

    override val highscore: Int
        get() {
            return max(_highscore, distance)
        }

    override val isDead: Boolean
        get() = world.isDead

    override val distance: Int
        get() = world.distance.toInt()

    private val world: GameWorld = GameWorld()

    private val gameRenderer = GameRenderer(world)
    private val uiRenderer = UIRenderer(this)

    private var accumulator: Float = 0.0f

    // This is some crazy kotlin-ass shit magic
    private var _highscore: Int = 0.apply {
        Gdx.app.getPreferences("motogame").getInteger("highscore")
    }

    override fun render(delta: Float) {
        gameRenderer.render(delta)
        uiRenderer.render(delta)
        checkInput()
        if (!world.isDead) {
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

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            reset()
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            DEBUG = !DEBUG
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
        if (score > prefs.getInteger("highscore")) {
            prefs.putInteger("highscore", score)
            _highscore = score
            prefs.flush()
        }
    }
}