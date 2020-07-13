package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.scenes.scene2d.Touchable
import se.nocroft.motogame.screen.GameService

class PausedMenuActor(private val gameService: GameService): BaseMenuActor() {

    override val buttons = arrayOf(
            Button("Resume").apply {
                onPress {
                    gameService.resume()
                }
                selected = true
            },
            Button("Reset").apply {
                onPress {
                    gameService.reset()
                }
            },
            Button("Exit to menu").apply {
                onPress {
                    gameService.exitToMenu()
                }
            }
    )

    init {
        add(Title("PAUSED"))
        buttons.forEach {
            row()
            add(it)
        }
        setFillParent(true)
        touchable = Touchable.enabled
    }
}