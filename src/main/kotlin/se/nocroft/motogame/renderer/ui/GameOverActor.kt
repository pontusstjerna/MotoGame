package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Value
import ktx.actors.onClick
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.PADDING_MEDIUM
import se.nocroft.motogame.TEXT_BUTTON_COLOR
import se.nocroft.motogame.screen.GameService

class GameOverActor(gameService: GameService, labelStyle: Label.LabelStyle): Table() {
    private val buttonLabelStyle = Label.LabelStyle().apply {
        font = BitmapFont()
        fontColor = TEXT_BUTTON_COLOR
    }

    private val gameOverLabel = Label("Whoops, you deaded. ", labelStyle)
    private val scoreLabel = Label("Your score: 0m", labelStyle)
    private val retryButton = Button("Retry", buttonLabelStyle).apply {
        onClick {
            gameService.reset()
        }
    }

    private val exitButton = Button("Exit", buttonLabelStyle)

    init {
        add(gameOverLabel)
        row()
        add(scoreLabel).pad(0f, 0f, PADDING_MEDIUM, 0f)
        row()
        add(retryButton).padBottom(Value.percentHeight(12.5f))
        //add(exitButton)
        setFillParent(true)
        debug = DEBUG
    }

    fun show(score: Int) {
        isVisible = true
        scoreLabel.setText("Your score: ${score}m")
    }
}