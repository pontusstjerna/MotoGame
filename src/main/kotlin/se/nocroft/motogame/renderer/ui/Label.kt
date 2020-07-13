package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import se.nocroft.motogame.TEXT_COLOR

private val labelStyle = Label.LabelStyle().apply {
    font = BitmapFont()
    fontColor = TEXT_COLOR
}

open class Label(text: CharSequence?) : com.badlogic.gdx.scenes.scene2d.ui.Label(text, labelStyle) {

}