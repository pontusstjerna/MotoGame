package model

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef

class Bike (position: Vector2, world: World) {

    // From: http://www.wpbracing.co.uk/wpb-beast
    val rearDamperLength = 0.456f
    val rearOffset = Vector2(-.625f, .535f)
    val frontOffset = Vector2(.625f, .535f)


    val rearWheel: Wheel = Wheel(position = position.cpy().add(rearOffset), world = world)
    val frontWheel: Wheel = Wheel(position = position.cpy().add(frontOffset), world = world)

    init {
        DistanceJointDef().apply {
            length = 1f
            initialize(rearWheel.body, frontWheel.body, rearWheel.body.position, frontWheel.body.position)
            world.createJoint(this)
        }
    }
}