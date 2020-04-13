package se.nocroft.motogame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.onClick
import ktx.actors.onKey
import ktx.actors.onKeyDown
import ktx.actors.stage
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.scene2d.table
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.PADDING_MEDIUM
import se.nocroft.motogame.TEXT_COLOR
import se.nocroft.motogame.renderer.ui.BaseMenuActor
import se.nocroft.motogame.renderer.ui.Button

class MenuScreen(menuService: MenuService) : KtxScreen {

    private val labelStyle = Label.LabelStyle().apply {
        font = BitmapFont()
        fontColor = TEXT_COLOR
    }

    private val stage = stage().apply {
        viewport = ScreenViewport()
        isDebugAll = DEBUG

        val table = object : BaseMenuActor() {

            override val buttons = arrayOf(
                    Button("Play", labelStyle).apply {
                        onPress {
                            menuService.play()
                        }
                        selected = true
                    },
                    Button("Exit", labelStyle).apply {
                        onPress {
                            menuService.exit()
                        }
                    })

        }.apply {
            add(Label("Infinite Moto", labelStyle)).expandY()
            row()
            add(table {
                for ((index, button) in buttons.withIndex()) {
                    add(button)
                    if (index != buttons.lastIndex) {
                        row()
                    }
                }
            }).expandY().top()
            row()
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