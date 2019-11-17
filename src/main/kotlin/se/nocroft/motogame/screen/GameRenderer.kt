package se.nocroft.motogame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import ktx.graphics.use
import ktx.math.*
import se.nocroft.motogame.model.Bike
import se.nocroft.motogame.model.GameWorld
import se.nocroft.motogame.model.Wheel
import kotlin.math.*

class GameRenderer(private val world: GameWorld) {

    private val batch = SpriteBatch()
    private val textBatch = SpriteBatch()
    private val font = BitmapFont()
    private val debugRenderer = Box2DDebugRenderer()
    private val camera = PerspectiveCamera().apply {
        // in meters
        val gameWidth = 20f
        val scale = gameWidth / Gdx.graphics.width

        //setToOrtho(false, gameWidth, Gdx.graphics.height * scale)
        viewportWidth = gameWidth
        viewportHeight = Gdx.graphics.height * scale
        fieldOfView = 57f
        rotate(-10f, 1f, 0f, 0f)
    }
    private val shapeRenderer: ShapeRenderer = ShapeRenderer()
    private val wheelTexture: Texture by lazy { Texture(Gdx.files.local("assets/wheel5.png")) }
    private val bikeTexture: Texture by lazy { Texture(Gdx.files.local("assets/bike_line1.png")) }
    private val bikeWheelWidthPixels = 93f

    private val depthZoom = 0.05f
    private val trackWidth = 1f

    private var deltaTimer: Float = 0.0f
    private var fps: Int = 0
    private var zoom: Float = 10f

    fun render(delta: Float) {
        //debugRenderer.render(world.physicsWorld, camera.combined)

        camera.position.set(
                world.bike.body.position.x + (distanceBetweenWheelsMeters() / 2f),
                world.bike.body.position.y + 3,
                getZoom())
        camera.update()

        renderTerrain(world.vertices)

        batch.projectionMatrix = camera.combined
        batch.use { b ->
            renderWheel(world.bike.frontWheel, b)
            renderWheel(world.bike.rearWheel, b)
            renderBike(world.bike, b)
        }

        renderUI(delta)
    }

    fun dispose() {
        batch.dispose()
        textBatch.dispose()
        wheelTexture.dispose()
    }

    // TODO: refactor to Scene2D
    private fun renderUI(delta: Float) {
        textBatch.use { b ->
            font.draw(b, "Distance: ${world.distance.roundToInt()} m", 20f, Gdx.graphics.height - 20f)
            font.draw(b, "FPS: $fps", 20f, Gdx.graphics.height - 40f)
            deltaTimer += delta
            if (deltaTimer > .1f) {
                fps = (1 / delta).toInt()
                deltaTimer = 0f
            }
        }
    }

    private fun getZoom(): Float {
        val goal = 10f + (world.bike.body.linearVelocity.len() * 0.5f)
        val zoomSpeed = 0.02f
        zoom += (goal - zoom) * zoomSpeed
        return zoom
    }

    private fun renderTerrain(vertices: List<Vector2>) {
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            val cameraPos = ImmutableVector2(camera.position.x, camera.position.y + 3f)
            for (i in 0 until vertices.lastIndex) {
                val fst = vertices[i].toImmutable()
                val snd = vertices[i + 1].toImmutable()

                //it.line(fst.toMutable(), snd.toMutable())
                it.line(Vector3(fst.toMutable(), -trackWidth), Vector3(snd.toMutable(), -trackWidth))
                it.line(Vector3(fst.toMutable(), trackWidth), Vector3(snd.toMutable(), trackWidth))
                it.line(Vector3(fst.toMutable(), -trackWidth), Vector3(fst.toMutable(), trackWidth))
            }
        }
    }

    private fun renderBike(bike: Bike, batch: SpriteBatch) {
        val bikeWheelsWidthMeters: Float = distanceBetweenWheelsMeters()
        val scale = bikeWheelsWidthMeters / bikeWheelWidthPixels
        val width = bikeTexture.width * scale
        val height = bikeTexture.height * scale
        batch.draw(
                bikeTexture,
                bike.body.position.x - (width / 2) + (width - bikeWheelsWidthMeters) - .1f, bike.body.position.y - (height / 2) - .15f,
                width / 2, height / 2, width, bikeTexture.height * scale, 1f, 1f,
                bike.body.angle * MathUtils.radiansToDegrees,
                0, 0, bikeTexture.width, bikeTexture.height, false, false
        )
    }

    private fun distanceBetweenWheelsMeters(): Float {
        val bike = world.bike
        return bike.frontWheel.body.position.let { front ->
            val rear = bike.rearWheel.body.position
            sqrt((front.x - rear.x).pow(2) + (front.y - rear.y).pow(2))
        }
    }

    private fun renderWheel(wheel: Wheel, batch: SpriteBatch) {
        batch.draw(
                this.wheelTexture,
                wheel.body.position.x - wheel.radius,
                wheel.body.position.y - wheel.radius,
                wheel.radius, wheel.radius,
                wheel.radius * 2,
                wheel.radius * 2,
                1f, 1f,
                wheel.body.angle * MathUtils.radiansToDegrees,
                0, 0,
                this.wheelTexture.width, this.wheelTexture.height, false, false
        )
    }
}