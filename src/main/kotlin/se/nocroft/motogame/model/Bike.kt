package se.nocroft.motogame.model

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import ktx.box2d.*

class Bike (private val position: Vector2, world: World) {

    // From: http://www.wpbracing.co.uk/wpb-beast
    val width = 1.25f
    val height = 0.525f
    val rearDamperLength = 0.456f
    val frontDamperLength = .77f
    val rearOffset = Vector2(-.625f, -.535f)
    val frontOffset = Vector2(.625f, -.535f)
    val topLeftOffset = Vector2(rearOffset.x, 0.2625f)
    val topRightOffset = Vector2(.3f, 0.36f)
    val bottomOffset = Vector2(0f, -.2625f)

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
    val frontWheel: Wheel = Wheel(position = position.cpy().add(frontOffset).add(frontOffset), world = world)

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