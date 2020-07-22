package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.scene2d.table
import ktx.actors.stage
import se.nocroft.motogame.*
import se.nocroft.motogame.screen.GameService
import kotlin.math.max

class UIRenderer(private val gameService: GameService) {

    private val distanceLabel = Label("Distance: ").apply { bold = true }
    private val distanceValue = Label("0m")
    private val bestLabel = Label("Best: ").apply { bold = true }
    private val bestValue = Label("0m")
    private val fpsLabel = Label("FPS: 0")
    private val gameOverActor = GameOverActor(gameService).apply { isVisible = false }
    private val pausedMenuActor = PausedMenuActor(gameService).apply { isVisible = false }
    private val menuPauseButton = Button("Pause").apply {
        onPress { togglePaused() }
    }

    private val topTable = table {
        add(bestLabel).left()
        add(bestValue).expandX().left()
        add(menuPauseButton).right()
        row()
        add(distanceLabel).left()
        add(distanceValue).left()
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
        viewport = ScreenViewport() //FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT)
        Gdx.input.inputProcessor = this
        isDebugAll = DEBUG

        addActor(topTable)
        addActor(gameOverActor)
        addActor(pausedMenuActor)

        addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                when (keycode) {
                    Input.Keys.ESCAPE -> togglePaused()
                    Input.Keys.P -> DEBUG = !DEBUG
                }
                return super.keyDown(event, keycode)
            }
        })
    }

    init {
        gameService.addGameEventListener { event ->
            when (event) {
                GameEvent.PAUSE -> {
                    pausedMenuActor.isVisible = true
                    menuPauseButton.setText("Resume")
                }
                GameEvent.DIE -> {
                    gameOverActor.isVisible = true
                    gameOverActor.show(gameService.distance.toInt())
                    topTable.isVisible = false
                }
                else -> {
                    pausedMenuActor.isVisible = false
                    menuPauseButton.setText("Pause")
                    topTable.isVisible = true
                    gameOverActor.isVisible = false

                }
            }
        }
    }

    fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    fun render(delta: Float) {
        distanceValue.setText("${gameService.distance.toInt()}m")
        bestValue.setText("${max(gameService.highScore, gameService.distance.toInt())}m")

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

        stage.keyboardFocus = when {
            gameService.isPaused -> pausedMenuActor
            gameService.isDead -> gameOverActor
            else -> stage.root
        }
    }

    fun dispose() {
        stage.dispose()
    }

    private fun togglePaused() {
        if (!gameService.isPaused) {
            gameService.pause()
        } else {
            gameService.resume()
        }
    }
}