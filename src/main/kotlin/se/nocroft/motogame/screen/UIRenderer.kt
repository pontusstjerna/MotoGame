package se.nocroft.motogame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.viewport.ScreenViewport
import se.nocroft.motogame.model.GameWorld

class UIRenderer(private val world: GameWorld) {
    private val stage = Stage().apply {
        viewport = ScreenViewport()
        Gdx.input.inputProcessor = this
    }

    private val labelStyle = Label.LabelStyle().apply {
        font = BitmapFont()
        fontColor = Color.WHITE
    }

    private val distanceLabel = Label("Distance: 0m", labelStyle).apply {
        setPosition(20f, Gdx.graphics.height - 40f)
    }

    private val fpsLabel = Label("FPS: 0", labelStyle).apply {
        setPosition(20f, Gdx.graphics.height - 60f)
    }

    private var deltaTimer: Float = 0.0f
    private var fps: Int = 0

    init {
        stage.addActor(distanceLabel)
        if (MotoGame.DEBUG) {
            stage.addActor(fpsLabel)
        }
    }

    fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    fun render(delta: Float) {
        distanceLabel.setText("Distance: ${world.bike.body.position.x.toInt()}m")
        if (MotoGame.DEBUG) {
            fpsLabel.setText("FPS: $fps")
        }

        stage.act(delta)
        stage.draw()

        deltaTimer += delta
        if (deltaTimer > .1f) {
            fps = (1 / delta).toInt()
            deltaTimer = 0f
        }
    }

    fun dispose() {
        stage.dispose()
    }
}