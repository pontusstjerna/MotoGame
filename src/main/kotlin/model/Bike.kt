package model

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import ktx.box2d.distanceJointWith

class Bike (position: Vector2, world: World) {

    // From: http://www.wpbracing.co.uk/wpb-beast
    val rearDamperLength = 0.456f
    val rearOffset = Vector2(-.625f, .535f)
    val frontOffset = Vector2(.625f, .535f)

    val thrust = 1f
    val breakThrust = 1f


    val rearWheel: Wheel = Wheel(position = position.cpy().add(rearOffset), world = world)
    val frontWheel: Wheel = Wheel(position = position.cpy().add(frontOffset), world = world)

    init {
        rearWheel.body.distanceJointWith(frontWheel.body) {
            length = frontOffset.x - rearOffset.x
        }
    }

    fun accelerate() {
        rearWheel.body.applyTorque(-thrust, true)
    }

    fun brake() {
        if (rearWheel.body.angularVelocity < 0) {
            rearWheel.body.applyTorque(breakThrust, true)
        } else if (rearWheel.body.angularVelocity > 0) {
            rearWheel.body.applyTorque(-breakThrust, true)
        }
    }
}