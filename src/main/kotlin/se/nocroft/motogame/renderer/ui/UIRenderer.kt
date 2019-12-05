package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.scene2d.table
import ktx.actors.stage
import ktx.app.clearScreen
import ktx.scene2d.label
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.PADDING_MEDIUM
import se.nocroft.motogame.TEXT_COLOR
import se.nocroft.motogame.model.GameWorld

class UIRenderer(private val world: GameWorld) {
    private val labelStyle = Label.LabelStyle().apply {
        font = BitmapFont()
        fontColor = TEXT_COLOR
    }

    private val distanceLabel = Label("Distance: 0m", labelStyle)
    private val fpsLabel = Label("FPS: 0", labelStyle)
    private val gameOverLabel = Label("Whoops, you deaded. Press enter key to retry...", labelStyle)
    private val gameOverActor: Actor = table {
        add(gameOverLabel).colspan(2)
        row().pad(0f, 0f, 10f, 0f)

        add(Label("Retry", labelStyle))
        add(Label("Exit", labelStyle))
        setFillParent(true)
        debug = DEBUG
    }

    private val stage = stage().apply {
        viewport = ScreenViewport()
        Gdx.input.inputProcessor = this

        val topTable = table {
            add(distanceLabel)
            if (DEBUG) {
                row()
                add(fpsLabel).left()
            }
            setFillParent(true)
            top().left().pad(PADDING_MEDIUM)
            debug = DEBUG
        }

        addActor(topTable)
        addActor(gameOverActor)
    }

    private var deltaTimer: Float = 0.0f
    private var fps: Int = 0

    fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    fun render(delta: Float) {
        distanceLabel.setText("Distance: ${world.bike.body.position.x.toInt()}m")
        if (DEBUG) {
            fpsLabel.setText("FPS: $fps")
        }

        if (world.isDead) {
            clearScreen(.12f,.12f,.12f, 1f)
        }

        stage.act(delta)
        stage.draw()

        deltaTimer += delta
        if (deltaTimer > .1f) {
            fps = (1 / delta).toInt()
            deltaTimer = 0f
        }

        gameOverActor.isVisible = world.isDead
    }

    fun dispose() {
        stage.dispose()
    }
}