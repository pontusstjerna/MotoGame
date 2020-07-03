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
    val topLeftOffset = Vector2(- (width / 2), .21f - (height / 2))
    val topRightOffset = Vector2(1.26f - (width / 2), 0.36f)
    val bottomOffset = Vector2(0f, -(height / 2))

    val maxThrust = 40f
    val thrust = 20f
    val brakeThrust = 20f

    val body = world.body(type = BodyDef.BodyType.DynamicBody) {
        position.set(this@Bike.position)
        polygon(topLeftOffset, topRightOffset, bottomOffset) {
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

    fun leanBack() {
        body.applyTorque(30f, true)
    }

    fun leanForward() {
        body.applyTorque(-30f, true)
    }
}