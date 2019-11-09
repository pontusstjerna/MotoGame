package model

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import ktx.box2d.BodyDefinition
import ktx.box2d.body
import ktx.box2d.circle

class Wheel(private val position: Vector2, world: World) {

    // From real world - 20 inch wheel
    val radius = 0.25f

    val body: Body = world.body(type = BodyDef.BodyType.DynamicBody, init = {
        circle(radius = radius, position = this@Wheel.position) {
            restitution = 0.5f
            friction = 0.1f
        }
    }).apply {
        circle(radius = radius) {
            restitution = 0.5f
        }
    }

}