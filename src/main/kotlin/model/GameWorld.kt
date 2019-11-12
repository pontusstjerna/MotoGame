package model

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ktx.box2d.body
import ktx.box2d.box
import ktx.box2d.createWorld
import ktx.box2d.earthGravity
import kotlin.random.Random

class GameWorld {

    val timeStep: Float = 1.0f / 60.0f

    val physicsWorld: World = createWorld(gravity = Vector2(0f, -9.81f))

    val segments: MutableList<Segment> = mutableListOf(
            Segment(from = Vector2(1.0f, 5f), to = Vector2(10f, 4f), world = physicsWorld)
    )

    val bike: Bike = Bike(Vector2(10f, 7f), physicsWorld)

    private var dy: Float = 0f
    private var ddy: Float = 0f

    private val VELOCITY_ITERATIONS = 8
    private val POSITION_ITERATIONS = 3
    private val generationThreshold = 5;

    fun update() {
        physicsWorld.step(timeStep, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
        generateTrack()
    }

    private fun generateTrack() {
        while (segments.last().to.x < bike.body.position.x + generationThreshold) {
            val last = segments.last()
            segments.add(Segment(
                    from = last.to,
                    to = Vector2(last.to.x + .5f, last.to.y + dy),
                    world = physicsWorld
            ))
            dy += ((Random.nextFloat() - 0.5f) * .1f )
            //dy += ddy * 0.1f
            //ddy = ddy + Random.nextFloat() - 0.5f
        }
    }

}