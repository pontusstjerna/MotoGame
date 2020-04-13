package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Table

abstract class BaseMenuActor : Table() {

    abstract val buttons: Array<Button>

    protected var selectedButtonIndex = 0
        set(value) {
            buttons[field].selected = false
            buttons[value].selected = true
            field = value
        }

    init {
        addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                when (keycode) {
                    Input.Keys.ENTER -> buttons[selectedButtonIndex].onPress?.invoke()
                    Input.Keys.UP -> {
                        if (selectedButtonIndex == 0) selectedButtonIndex = buttons.lastIndex else selectedButtonIndex--
                    }
                    Input.Keys.DOWN -> {
                        selectedButtonIndex = (selectedButtonIndex + 1) % buttons.size
                    }
                }
                return super.keyDown(event, keycode)
            }
        })
    }

}