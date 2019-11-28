package se.nocroft.motogame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.PADDING_MEDIUM
import se.nocroft.motogame.TEXT_COLOR
import se.nocroft.motogame.model.GameWorld

class UIRenderer(private val world: GameWorld) {
    private val stage = Stage().apply {
        viewport = ScreenViewport()
        Gdx.input.inputProcessor = this
    }

    private val table = Table().apply {
        setFillParent(true)
        debug = DEBUG
    }

    private val labelStyle = Label.LabelStyle().apply {
        font = BitmapFont()
        fontColor = TEXT_COLOR
    }

    private val distanceLabel = Label("Distance: 0m", labelStyle)
    private val fpsLabel = Label("FPS: 0", labelStyle)

    private var deltaTimer: Float = 0.0f
    private var fps: Int = 0

    init {
        table.run {
            add(distanceLabel)
            if (DEBUG) {
                row()
                add(fpsLabel).left()
            }

            top().left().pad(PADDING_MEDIUM)
        }
        stage.addActor(table)
    }

    fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    fun render(delta: Float) {
        distanceLabel.setText("Distance: ${world.bike.body.position.x.toInt()}m")
        if (DEBUG) {
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