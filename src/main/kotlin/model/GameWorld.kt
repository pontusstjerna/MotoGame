package model

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ktx.box2d.body
import ktx.box2d.box
import ktx.box2d.createWorld
import ktx.box2d.earthGravity

class GameWorld {

    val timeStep: Float = 1.0f / 60.0f

    val physicsWorld: World = createWorld(gravity = earthGravity)

    val segment = Segment(from = Vector2(2.0f, 50f), to = Vector2(800f, 50f), world = physicsWorld)

    // TODO: refactor
    lateinit var dynamicBody: Body

    private val VELOCITY_ITERATIONS = 8
    private val POSITION_ITERATIONS = 3

    fun create() {

        // CREATE DYNAMIC BODY
        dynamicBody = physicsWorld.body(type = BodyDef.BodyType.DynamicBody) {
            position.set(100f, 300f)
        }

        val dynamicBox: Shape = PolygonShape().apply {
            setAsBox(10f,10f)
        }

        val fixtureDef: FixtureDef = FixtureDef().apply {
            shape = dynamicBox
            density = 1.0f
            friction = 0.3f
        }

        val dynamicFixture: Fixture = dynamicBody.createFixture(fixtureDef)
        dynamicBox.dispose()
    }

    fun update() {
        physicsWorld.step(timeStep, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
    }

}