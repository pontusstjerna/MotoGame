package se.nocroft.motogame.model

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ktx.box2d.createWorld
import java.lang.Float.max
import kotlin.random.Random

class GameWorld: ContactListener {

    val timeStep: Float = 1.0f / 60.0f

    var score: Float = 0.0f
    private set

    val physicsWorld: World = createWorld(gravity = Vector2(0f, -9.81f)).apply {
        setContactListener(this@GameWorld)
    }

    val vertices: MutableList<Vector2> = mutableListOf(
            Vector2(-10.0f, 2f),
            Vector2(5f, 2f),
            Vector2(10f, 2f)
    )

    private val initBikePos = Vector2(0f, 7f)

    val bike: Bike = Bike(initBikePos, physicsWorld)

    private var dy: Float = 0f
    private var ddy: Float = 0f

    private val VELOCITY_ITERATIONS = 8
    private val POSITION_ITERATIONS = 3
    private val generationThreshold = 5

    // GENERATION CONFIG
    private val bigSlopeFactor = 0.03f
    private val smallSlopeFactor = 0.03f
    private val bigRandomFactor = 0.1f
    private val smallRandomFactor = 0.1f
    private val bumps = 0.2f // smaller value = more bumps!

    init {
        createSegment(vertices.first(), vertices[1], null, vertices.last(), physicsWorld)
    }

    fun update() {
        physicsWorld.step(timeStep, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
        generateTrack()
        score = max(score, bike.body.position.x)
    }

    override fun endContact(contact: Contact?) {

    }

    override fun beginContact(contact: Contact?) {
        contact?.let {

            // Why the hell do a comparison like this? BECAUSE I CAN, THAT'S WHY
            if (bike.body.run { equals(it.fixtureA.body) || equals(it.fixtureB.body) }) {
                // TODO: Crash
            }
        }
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {

    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {

    }

    private fun generateTrack() {
        while (vertices.last().x < bike.body.position.x + generationThreshold) {
            val last = vertices.last()
            vertices.add(Vector2(last.x + Random.nextFloat() + bumps, last.y + dy))
            dy += ddy * smallRandomFactor
            ddy += ((Random.nextFloat() - .5f) * bigRandomFactor) - (dy * bigSlopeFactor) - (ddy * smallSlopeFactor)

            val ghostTo = vertices.last()
            val to = if (vertices.size > 2) last else vertices.last()
            val from = vertices[vertices.lastIndex - 2]
            val ghostFrom = if (vertices.size > 3) vertices[vertices.lastIndex - 3] else null

            createSegment(from, to, ghostFrom, ghostTo, physicsWorld)
        }
    }
}