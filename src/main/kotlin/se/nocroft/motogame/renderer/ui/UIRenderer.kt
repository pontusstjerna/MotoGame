package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.scene2d.table
import ktx.actors.stage
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.PADDING_MEDIUM
import se.nocroft.motogame.TEXT_COLOR
import se.nocroft.motogame.screen.GameService
import kotlin.math.max

class UIRenderer(private val gameService: GameService) {

    private val labelStyle = Label.LabelStyle().apply {
        font = BitmapFont()
        fontColor = TEXT_COLOR
    }

    private val distanceLabel = Label("Distance: 0m", labelStyle)
    private val bestLabel = Label("Best: 0m", labelStyle)
    private val fpsLabel = Label("FPS: 0", labelStyle)
    private val gameOverActor = GameOverActor(gameService, labelStyle).apply { isVisible = false }
    private val pausedMenuActor = PausedMenuActor(gameService, labelStyle).apply { isVisible = false }
    private val menuPauseButton = Button("Pause", labelStyle).apply {
        onPress { togglePaused() }
    }

    private val topTable = table {
        add(distanceLabel).expandX().left()
        add(menuPauseButton).right()
        row()
        add(bestLabel).left()
        if (DEBUG) {
            row()
            add(fpsLabel).left()
        }
        setFillParent(true)
        top().pad(PADDING_MEDIUM)
    }

    private var deltaTimer: Float = 0.0f
    private var fps: Int = 0

    val stage = stage().apply {
        viewport = ScreenViewport()
        Gdx.input.inputProcessor = this
        isDebugAll = DEBUG

        addActor(topTable)
        addActor(gameOverActor)
        addActor(pausedMenuActor)

        addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                when (keycode) {
                    Input.Keys.ESCAPE -> togglePaused()
                    Input.Keys.ENTER -> { gameService.reset() }
                    Input.Keys.P -> DEBUG = !DEBUG
                }
                return super.keyDown(event, keycode)
            }
        })
    }

    fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    fun render(delta: Float) {
        distanceLabel.setText("Distance: ${gameService.distance}m")
        bestLabel.setText("Best: ${max(gameService.highscore, gameService.distance)}m")

        if (DEBUG) {
            fpsLabel.setText("FPS: $fps")
        }

        if (gameService.isDead && !gameOverActor.isVisible) {
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

        topTable.isVisible = !gameService.isDead
        pausedMenuActor.isVisible = gameService.isPaused && !gameService.isDead
        gameOverActor.isVisible = gameService.isDead
    }

    fun dispose() {
        stage.dispose()
    }

    private fun togglePaused() {
        if (!gameService.isPaused) {
            menuPauseButton.setText("Resume")
            gameService.pause()
        } else {
            menuPauseButton.setText("Pause")
            gameService.resume()
        }
    }
}