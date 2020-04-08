package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import ktx.actors.onClick

class Button(var text: String, style: LabelStyle?) : Label(" [ $text ] ", style) {

    var onPress: (() -> Unit)? = null

    fun onPress(action: () -> Unit) {
        onClick {
            action()
        }

        onPress = action
    }

    var selected: Boolean = false
        set(value) {
            super.setText(if (value) "[  $text  ]" else " [ $text ] ")
            field = value
        }

    override fun setText(newText: CharSequence?) {
        text = newText.toString()
        return super.setText(" [ $newText ] ")
    }

    init {
        addListener(object : ClickListener() {
            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                if (!selected) super@Button.setText("[  $text  ]")
            }

            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                if (!selected) super@Button.setText(" [ $text ] ")
            }
        })
    }

}