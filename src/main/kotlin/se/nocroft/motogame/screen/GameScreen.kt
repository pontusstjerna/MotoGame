package se.nocroft.motogame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import ktx.app.KtxScreen
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.model.GameWorld
import se.nocroft.motogame.renderer.GameRenderer
import se.nocroft.motogame.renderer.ui.UIRenderer

// https://github.com/Quillraven/SimpleKtxGame/blob/01-app/core/src/com/libktx/game/screen/GameScreen.kt

class GameScreen : KtxScreen {

    private val world: GameWorld = GameWorld()

    private val gameRenderer = GameRenderer(world)
    private val uiRenderer = UIRenderer(world)

    private var accumulator: Float = 0.0f

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
            world.reset()
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
}