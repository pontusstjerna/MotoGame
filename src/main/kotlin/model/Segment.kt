package model

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.World
import ktx.box2d.body
import ktx.box2d.box

class Segment(world: World, x: Float, y: Float, width: Float, height: Float) {

    private val body: Body = world.body {
        box(width = width, height = height)
    }

    val fixture: Fixture = body.box (width = width, height = height) {

    }

}