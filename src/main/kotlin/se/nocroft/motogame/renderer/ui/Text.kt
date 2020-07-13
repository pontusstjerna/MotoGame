package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import se.nocroft.motogame.TEXT_COLOR

val BASE_PATH = "assets/fonts"
private class Regular : Label.LabelStyle() {
    init {
        fontColor = TEXT_COLOR
        font = BitmapFont(Gdx.files.local("$BASE_PATH/exo2_regular_16.fnt"))
    }
}

private class Bold : Label.LabelStyle() {
    init {
        fontColor = TEXT_COLOR
        font = BitmapFont(Gdx.files.local("$BASE_PATH/exo2_bold_16.fnt"))
    }
}

open class Label(text: CharSequence?) : com.badlogic.gdx.scenes.scene2d.ui.Label(text, Regular()) {
    var bold = false
        set(value) {
            style = if (value) Bold() else Regular()
            field = value
        }
}

class Title(text: CharSequence?) : com.badlogic.gdx.scenes.scene2d.ui.Label(text, Bold())
