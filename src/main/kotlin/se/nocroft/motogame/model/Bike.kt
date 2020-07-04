package se.nocroft.motogame.model

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import ktx.box2d.*

class Bike (private val position: Vector2, world: World) {

    val width = 1.5f
    val height = 0.71f
    val rearDamperLength = 0.456f
    val frontDamperLength = .77f
    val rearOffset = Vector2(.26f - (width / 2),  -(height / 2) + (height - .68f) )
    val frontOffset = Vector2(1.32f - (width / 2), -(height / 2) + (height - .67f))
    val topLeftOffset = Vector2(.2f - (width / 2), .51f - (height / 2))
    val topRightOffset = Vector2(1.5f - (width / 2), 0.4f - (height / 2))
    val bottomOffset = Vector2(0f, -(height / 2))

    val maxThrust = 40f
    val thrust = 20f
    val brakeThrust = 20f

    val rider = Rider()

    val headOffset = Vector2(0f, rider.height - rider.crotchY)

    val body = world.body(type = BodyDef.BodyType.DynamicBody) {
        position.set(this@Bike.position)
        polygon(topLeftOffset, headOffset, topRightOffset, bottomOffset) {
            density = 10f
        }
    }

    val rearWheel: Wheel = Wheel(position = position.cpy().add(rearOffset), world = world)
    val frontWheel: Wheel = Wheel(position = position.cpy().add(frontOffset), world = world)

    val wheelThrust: Float
        get() = - rearWheel.body.angularVelocity

    fun destroy(with: World) {
        with.destroyBody(body)
        with.destroyBody(rearWheel.body)
        with.destroyBody(frontWheel.body)
    }

    private val rearWheelJoint = rearWheel.body.revoluteJointWith(body) {
        localAnchorB.set(rearOffset)
        //enableMotor = true
        //maxMotorTorque = 100f
        //motorSpeed = 500f
    }

    init {
        frontWheel.body.revoluteJointWith(body) {
            localAnchorB.set(frontOffset)
        }
    }

    fun update() {
        if (rider.leaningForward) {
            body.applyTorque(-30f, true)
        } else if (rider.leaningBackward) {
            body.applyTorque(30f, true)
        }
    }

    fun accelerate() {
        if (rearWheel.body.angularVelocity > -maxThrust) {
            rearWheel.body.applyTorque(-thrust, true)
        }
        //rearWheelJoint.motorSpeed = 10f
    }

    fun brake() {
        if (rearWheel.body.angularVelocity < 0) {
            rearWheel.body.applyTorque(brakeThrust, true)
        } else if (rearWheel.body.angularVelocity > 0) {
            rearWheel.body.applyTorque(-brakeThrust, true)
        }
    }
}