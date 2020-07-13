package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import se.nocroft.motogame.TEXT_COLOR

val BASE_PATH = "assets/fonts"
private val regularFont = BitmapFont(Gdx.files.local("$BASE_PATH/exo2_regular_16.fnt"))
private val boldFont = BitmapFont(Gdx.files.local("$BASE_PATH/exo2_bold_16.fnt"))

private val labelStyle = Label.LabelStyle().apply {
    fontColor = TEXT_COLOR
}

open class Label(text: CharSequence?, bold: Boolean = false) : com.badlogic.gdx.scenes.scene2d.ui.Label(
        text,
        labelStyle.apply {
            font = if (bold)  boldFont else regularFont
        }
)

class Title(text: CharSequence?) : com.badlogic.gdx.scenes.scene2d.ui.Label(text, labelStyle.apply { font = boldFont })
