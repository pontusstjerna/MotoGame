package se.nocroft.motogame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.stage
import ktx.app.KtxScreen
import ktx.app.clearScreen
import se.nocroft.motogame.*
import se.nocroft.motogame.renderer.ui.BaseMenuActor
import se.nocroft.motogame.renderer.ui.Button
import se.nocroft.motogame.renderer.ui.Label
import se.nocroft.motogame.renderer.ui.Title
import se.nocroft.motogame.util.getHighScores

class HighScoreScreen(private val menuService: MenuService) : KtxScreen {

    private val highScoresTable = Table()

    private val stage = stage().apply {
        viewport = FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT)
        isDebugAll = DEBUG

        val table = object : BaseMenuActor() {

            override val buttons = arrayOf(
                    Button("Back to menu").apply {
                        onPress {
                            menuService.goToMenu()
                        }
                    })

        }.apply {
            add(Title("HIGH SCORES")).padBottom(PADDING_SMALL)
            row()
            add(highScoresTable)
            row()
            add(buttons.first()).padTop(PADDING_SMALL)
            setFillParent(true)
        }

        addActor(table)
        keyboardFocus = table
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
        reloadHighScores()
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

    private fun reloadHighScores() {
        highScoresTable.clearChildren()
        buildHighScoresTable(highScoresTable)
    }

    private fun buildHighScoresTable(table: Table) {
        val highScores = getHighScores()
        highScores.withIndex().forEach { (index, highScore) ->
            table.row()
            table.add(Label("${index + 1}. ").apply { bold = true }).right()
            table.add(Label("${highScore}m")).left()
        }
        if (highScores.isEmpty()) {
            table.row()
            table.add(Label("No high scores yet!"))
        }
    }
}