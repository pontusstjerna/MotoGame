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

    private val VELOCITY_ITERATIONS = 8
    private val POSITION_ITERATIONS = 3

    private val physicsWorld: World = createWorld(gravity = earthGravity)

    fun create() {

        // CREATE GROUND BODY
        // Long version
        /*val groundBodyDef: BodyDef = BodyDef().apply {
            position.set(0f, 0f)
        }
        val groundBody: Body = physicsWorld.createBody(groundBodyDef)*/

        val groundBody: Body = physicsWorld.body {
            position.set(0f, 0f)
        }

        val groundBox: Shape = PolygonShape().apply {
            setAsBox(50f, 20f)
        }

        val groundFixture: Fixture = groundBody.createFixture(groundBox, 0.0f)

        // CREATE DYNAMIC BODY
        dynamicBody = physicsWorld.body(type = BodyDef.BodyType.DynamicBody) {
            position.set(10f, 10f)
        }

        val dynamicBox: Shape = PolygonShape().apply {
            setAsBox(1f,1f)
        }

        val fixtureDef: FixtureDef = FixtureDef().apply {
            shape = dynamicBox
            density = 1.0f
            friction = 0.3f
        }

        val dynamicFixture: Fixture = dynamicBody.createFixture(fixtureDef)
    }

    fun update() {
        physicsWorld.step(timeStep, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
    }

}