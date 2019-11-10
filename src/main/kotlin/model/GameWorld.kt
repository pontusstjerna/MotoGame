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

    val physicsWorld: World = createWorld(gravity = earthGravity)

    val segments: List<Segment> = mutableListOf(
            Segment(from = Vector2(1.0f, 5f), to = Vector2(10f, 4f), world = physicsWorld)
    ).let {
        for(i in 1..50) {
            val last = it.last()
            it.add(Segment(
                    from = last.to,
                    to = Vector2(last.to.x + 10f, last.to.y - 2f + Random.nextInt(-1, 1)),
                    world = physicsWorld
            ))
        }

        // A little jump
        it.add(Segment(it.last().to, Vector2(it.last().to.x + 50f, it.last().to.y + 10f), physicsWorld))
        it.toList()
    }

    val bike: Bike = Bike(Vector2(10f, 10f), physicsWorld)

    private val VELOCITY_ITERATIONS = 8
    private val POSITION_ITERATIONS = 3

    fun update() {
        physicsWorld.step(timeStep, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
    }

}