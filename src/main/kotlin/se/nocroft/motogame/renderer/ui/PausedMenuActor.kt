package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Label
import se.nocroft.motogame.TEXT_BUTTON_COLOR
import se.nocroft.motogame.screen.GameService

class PausedMenuActor(private val gameService: GameService, labelStyle: Label.LabelStyle): BaseMenuActor() {

    private val buttonLabelStyle = Label.LabelStyle().apply {
        font = BitmapFont()
        fontColor = TEXT_BUTTON_COLOR
    }

    override val buttons = arrayOf(
            Button("Resume", buttonLabelStyle).apply {
                onPress {
                    gameService.resume()
                }
                selected = true
            },
            Button("Reset", buttonLabelStyle).apply {
                onPress {
                    gameService.reset()
                }
            },
            Button("Exit to menu", buttonLabelStyle).apply {
                onPress {
                    gameService.exitToMenu()
                }
            }
    )

    init {
        add(Label("Paused", labelStyle))
        buttons.forEach {
            row()
            add(it)
        }
        setFillParent(true)
        touchable = Touchable.enabled
    }
}