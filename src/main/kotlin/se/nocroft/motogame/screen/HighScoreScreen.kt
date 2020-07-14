package se.nocroft.motogame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.stage
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.scene2d.table
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.PADDING_MEDIUM
import se.nocroft.motogame.PADDING_SMALL
import se.nocroft.motogame.renderer.ui.BaseMenuActor
import se.nocroft.motogame.renderer.ui.Button
import se.nocroft.motogame.renderer.ui.Label
import se.nocroft.motogame.renderer.ui.Title
import se.nocroft.motogame.util.getHighScores

class HighScoreScreen(private val menuService: MenuService) : KtxScreen {

    private val stage = stage().apply {
        viewport = ScreenViewport()
        isDebugAll = DEBUG

        val table = object : BaseMenuActor() {

            override val buttons = arrayOf(
                    Button("Back to menu").apply {
                        onPress {
                            menuService.goToMenu()
                        }
                    })

        }.apply {
            add(Title("HIGH SCORES")).padBottom(PADDING_SMALL).colspan(2)
            val highScores = getHighScores()
            highScores.withIndex().forEach { (index, highScore) ->
                row()
                add(Label("${index + 1}.").apply { bold = true }).left()
                add(Label("${highScore}m")).right()
            }
            if (highScores.isEmpty()) {
                row()
                add(Label("No high scores yet!"))
            }
            row()
            add(buttons.first()).colspan(2).padTop(PADDING_SMALL)
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