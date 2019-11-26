package se.nocroft.motogame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.viewport.ScreenViewport

class UIRenderer() {
    private val stage = Stage().apply {
        viewport = ScreenViewport()
        Gdx.input.inputProcessor = this

        val labelStyle = Label.LabelStyle()
        labelStyle.font = BitmapFont()
        labelStyle.fontColor = Color.RED

        val label = Label("I like cookies", labelStyle)
        label.setPosition(10f, Gdx.graphics.height - 20f)
        addActor(label)
    }

    fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    fun render(delta: Float) {
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()
    }

    fun dispose() {
        stage.dispose()
    }
}