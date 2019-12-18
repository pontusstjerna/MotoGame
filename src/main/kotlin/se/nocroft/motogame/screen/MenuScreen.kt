package se.nocroft.motogame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.onClick
import ktx.actors.stage
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.scene2d.table
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.PADDING_MEDIUM
import se.nocroft.motogame.TEXT_COLOR
import se.nocroft.motogame.renderer.ui.Button

class MenuScreen(menuService: MenuService) : KtxScreen {

    private val labelStyle = Label.LabelStyle().apply {
        font = BitmapFont()
        fontColor = TEXT_COLOR
    }

    private val table = table {

        add(Label("Infinite Moto", labelStyle)).expandY()
        row()

        table {
            val playButton = Button("Play", labelStyle).apply {
                onClick {
                    menuService.play()
                }
            }

            val exitButton = Button("Exit", labelStyle).apply {
                onClick {
                    menuService.exit()
                }
            }

            add(playButton)
            row()
            add(exitButton)
        }.cell(expandY = true).inCell.top()

        row()


        setFillParent(true)
    }

    private val stage = stage().apply {
        viewport = ScreenViewport()
        Gdx.input.inputProcessor = this
        isDebugAll = DEBUG

        addActor(table)
    }

    override fun render(delta: Float) {
        clearScreen(.12f,.12f,.12f, .5f)
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