package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Value
import ktx.actors.onClick
import se.nocroft.motogame.TEXT_BUTTON_COLOR
import se.nocroft.motogame.screen.GameService
import kotlin.random.Random

class PausedMenuActor(private val gameService: GameService, labelStyle: Label.LabelStyle): Table() {

    private val buttonLabelStyle = Label.LabelStyle().apply {
        font = BitmapFont()
        fontColor = TEXT_BUTTON_COLOR
    }

    init {
        add(Label("Paused", labelStyle))
        row()
        add(Button("Resume", buttonLabelStyle).apply {
            onPress {
                gameService.resume()
            }
        })
        row()
        add(Button("Reset", buttonLabelStyle).apply {
            onPress {
                gameService.reset()
            }
        })
        row()
        add(Button("Exit to menu", buttonLabelStyle).apply {
            onPress {
                gameService.exitToMenu()
            }
        })
        setFillParent(true)
        touchable = Touchable.enabled

        addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                when (keycode) {
                    Input.Keys.UP -> println("shit")
                }
                return super.keyDown(event, keycode)
            }
        })
    }
}