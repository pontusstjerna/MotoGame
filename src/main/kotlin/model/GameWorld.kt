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

    val vertices: MutableList<Vector2> = mutableListOf(
            Vector2(-10.0f, 2f),
            Vector2(5f, 2f),
            Vector2(10f, 2f)
    )

    val bike: Bike = Bike(Vector2(0f, 7f), physicsWorld)

    private var dy: Float = 0f
    private var ddy: Float = 0f

    private val VELOCITY_ITERATIONS = 8
    private val POSITION_ITERATIONS = 3
    private val generationThreshold = 5

    init {
        createSegment(vertices.first(), vertices[1], null, vertices.last(), physicsWorld)
    }

    fun update() {
        physicsWorld.step(timeStep, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
        generateTrack()
    }

    private fun generateTrack() {
        while (vertices.last().x < bike.body.position.x + generationThreshold) {
            val last = vertices.last()
            vertices.add(Vector2(last.x /* + Random.nextFloat()*/ + .5f, last.y + dy))
            dy += ddy * 0.1f
            ddy += ((Random.nextFloat() - .5f) * .1f) - (dy * 0.1f) - (ddy * 0.03f)
            //dy += ((Random.nextFloat() - .5f) * .1f) - (dy * 0.4f)

            val ghostTo = vertices.last()
            val to = if (vertices.size > 2) last else vertices.last()
            val from = vertices[vertices.lastIndex - 2]
            val ghostFrom = if (vertices.size > 3) vertices[vertices.lastIndex - 3] else null

            createSegment(from, to, ghostFrom, ghostTo, physicsWorld)
            /*segments.add(Segment(
                    from = last.to,
                    to = Vector2(last.to.x /*+ Random.nextFloat()*/ + .5f, last.to.y + dy),
                    world = physicsWorld
            ))*/
        }
    }

}