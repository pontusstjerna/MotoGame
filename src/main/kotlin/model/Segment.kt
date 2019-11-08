package model

import com.badlogic.gdx.physics.box2d.*
import ktx.box2d.body
import ktx.box2d.box

class Segment(world: World, x: Float, y: Float, width: Float, height: Float) {

    val body: Body = world.body(type = BodyDef.BodyType.DynamicBody) {
        box(width = width, height = height)
    }

    val fixture: Fixture = body.box (width = width / 2, height = height / 2) {
        density = 1.0f
        friction = 0.3f
    }
}