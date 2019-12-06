package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import ktx.actors.onClick
import ktx.scene2d.table
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.TEXT_BUTTON_COLOR

class GameOverActor(labelStyle: Label.LabelStyle): Table() {
    private val buttonLabelStyle = Label.LabelStyle().apply {
        font = BitmapFont()
        fontColor = TEXT_BUTTON_COLOR
    }

    private val gameOverLabel = Label("Whoops, you deaded. ", labelStyle)
    private val scoreLabel = Label("Your score: 0m", labelStyle)
    private val retryButton = Label("[Retry]", buttonLabelStyle).apply {
        onClick {
            // call method from interface/protocol
        }

        // TODO: Expand [ ] on hover
        addListener(object : ClickListener() {

        })

    }
    private val exitButton = Label("[Exit]", buttonLabelStyle)

    init {
        add(gameOverLabel).colspan(2)
        row()
        add(scoreLabel).colspan(2).pad(0f, 0f, 20f, 0f)
        row()
        add(retryButton)
        add(exitButton)
        setFillParent(true)
        debug = DEBUG
    }

    fun show(score: Float) {
        scoreLabel.setText("Your score: ${score}m")
    }
}