package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.scene2d.table
import ktx.actors.stage
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.PADDING_MEDIUM
import se.nocroft.motogame.TEXT_COLOR
import se.nocroft.motogame.screen.GameService

class UIRenderer(private val gameService: GameService) {
    private val labelStyle = Label.LabelStyle().apply {
        font = BitmapFont()
        fontColor = TEXT_COLOR
    }

    private val distanceLabel = Label("Distance: 0m", labelStyle)
    private val fpsLabel = Label("FPS: 0", labelStyle)
    private val gameOverLabel = Label("Whoops, you deaded. ", labelStyle)
    private val scoreLabel = Label("Your score: 0m", labelStyle)
    private val gameOverActor = GameOverActor(gameService, labelStyle).apply {
        isVisible = false
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
        distanceLabel.isVisible = !gameService.isDead
        distanceLabel.setText("Distance: ${gameService.distance}m")
        if (DEBUG) {
            fpsLabel.setText("FPS: $fps")
        }

        if (gameService.isDead) {
            gameOverActor.show(gameService.distance)
            //clearScreen(.12f,.12f,.12f, .5f)
        }

        stage.act(delta)
        stage.draw()

        deltaTimer += delta
        if (deltaTimer > .1f) {
            fps = (1 / delta).toInt()
            deltaTimer = 0f
        }

        gameOverActor.isVisible = gameService.isDead
    }

    fun dispose() {
        stage.dispose()
    }
}