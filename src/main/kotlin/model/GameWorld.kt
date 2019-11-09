package model

import com.badlogic.gdx.physics.box2d.*
import ktx.box2d.body
import ktx.box2d.box
import ktx.box2d.createWorld
import ktx.box2d.earthGravity

class GameWorld {

    val timeStep: Float = 1.0f / 60.0f

    val segment: Segment by lazy { Segment(physicsWorld, 50f, 50f, 10f, 10f) }

    // TODO: refactor
    lateinit var dynamicBody: Body
    lateinit var groundBody: Body

    private val VELOCITY_ITERATIONS = 8
    private val POSITION_ITERATIONS = 3

    val physicsWorld: World = createWorld(gravity = earthGravity)

    fun create() {

        // CREATE GROUND BODY
        // Long version
        /*val groundBodyDef: BodyDef = BodyDef().apply {
            position.set(0f, 0f)
        }
        val groundBody: Body = physicsWorld.createBody(groundBodyDef)*/

        groundBody = physicsWorld.body {
            position.set(0f, 10f)
        }

        val groundBox: Shape = PolygonShape().apply {
            setAsBox(800f, 10f)
        }

        val groundFixture: Fixture = groundBody.createFixture(groundBox, 0.0f)
        groundBox.dispose()

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