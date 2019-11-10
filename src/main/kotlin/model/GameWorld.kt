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

    val segments: Array<Segment> = arrayOf(
        Segment(from = Vector2(1.0f, 5f), to = Vector2(10f, 4f), world = physicsWorld),
        Segment(from = Vector2(10f, 4f), to = Vector2(25f, 6f), world = physicsWorld)
    )

    val wheels: Array<Wheel> = arrayOf(
        Wheel(position = Vector2(10f, 20f), world = physicsWorld),
        Wheel(position = Vector2(5f, 20f), world = physicsWorld),
        Wheel(position = Vector2(8f, 10f), world = physicsWorld),
        Wheel(position = Vector2(4f, 8f), world = physicsWorld)
    )

    private val VELOCITY_ITERATIONS = 8
    private val POSITION_ITERATIONS = 3

    fun update() {
        physicsWorld.step(timeStep, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
    }

}