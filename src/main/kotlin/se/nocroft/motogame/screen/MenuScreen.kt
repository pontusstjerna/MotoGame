package se.nocroft.motogame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.stage
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.scene2d.table
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.PADDING_SMALL
import se.nocroft.motogame.WINDOW_HEIGHT
import se.nocroft.motogame.WINDOW_WIDTH
import se.nocroft.motogame.renderer.ui.BaseMenuActor
import se.nocroft.motogame.renderer.ui.Button
import se.nocroft.motogame.renderer.ui.Label
import se.nocroft.motogame.renderer.ui.Title

class MenuScreen(menuService: MenuService) : KtxScreen {

    private val stage = stage().apply {
        viewport = FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT)
        isDebugAll = DEBUG

        val table = object : BaseMenuActor() {

            override val buttons = arrayOf(
                    Button("Play").apply {
                        onPress {
                            menuService.play()
                        }
                        selected = true
                    },
                    Button("High scores").apply {
                        onPress {
                            menuService.goToHighScores()
                        }
                    },
                    Button("Exit").apply {
                        onPress {
                            menuService.exit()
                        }
                    })

        }.apply {
            add(Title("INFINITE MOTO")).padBottom(PADDING_SMALL)
            for (button in buttons) {
                row()
                add(button)
            }
            setFillParent(true)
        }

        addActor(table)
        keyboardFocus = table
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
        super.show()
    }

    override fun render(delta: Float) {
        clearScreen(.12f, .12f, .12f, .5f)
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.dispose()
    }
}