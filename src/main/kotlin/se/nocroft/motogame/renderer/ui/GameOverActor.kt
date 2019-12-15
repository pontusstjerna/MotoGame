package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import ktx.actors.onClick
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.TEXT_BUTTON_COLOR
import se.nocroft.motogame.model.WorldService

class GameOverActor(worldService: WorldService, labelStyle: Label.LabelStyle): Table() {
    private val buttonLabelStyle = Label.LabelStyle().apply {
        font = BitmapFont()
        fontColor = TEXT_BUTTON_COLOR
    }

    private val gameOverLabel = Label("Whoops, you deaded. ", labelStyle)
    private val scoreLabel = Label("Your score: 0m", labelStyle)
    private val retryButton = Button("Retry", buttonLabelStyle).apply {
        onClick {
            worldService.reset()
        }
    }

    private val exitButton = Button("Exit", buttonLabelStyle)

    init {
        add(gameOverLabel)
        row()
        add(scoreLabel).pad(0f, 0f, 20f, 0f)
        row()
        add(retryButton).padBottom(150f)
        //add(exitButton)
        setFillParent(true)
        debug = DEBUG
    }

    fun show(score: Int) {
        isVisible = true
        scoreLabel.setText("Your score: ${score}m")
    }
}