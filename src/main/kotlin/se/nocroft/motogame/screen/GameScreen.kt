package se.nocroft.motogame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import ktx.app.KtxScreen
import ktx.graphics.use
import se.nocroft.motogame.model.Bike
import se.nocroft.motogame.model.GameWorld
import se.nocroft.motogame.model.Wheel
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

// https://github.com/Quillraven/SimpleKtxGame/blob/01-app/core/src/com/libktx/game/screen/GameScreen.kt

class GameScreen : KtxScreen {

    private val world: GameWorld = GameWorld()

    private val batch = SpriteBatch()
    private val textBatch = SpriteBatch()
    private val font = BitmapFont()
    private val debugRenderer = Box2DDebugRenderer()
    private val camera = OrthographicCamera().apply {
        // in meters
        val gameWidth = 20f
        val scale = gameWidth / Gdx.graphics.width

        setToOrtho(false, gameWidth, Gdx.graphics.height * scale)
    }
    private val shapeRenderer: ShapeRenderer = ShapeRenderer()
    private val wheelTexture: Texture by lazy { Texture(Gdx.files.local("assets/wheel4.png")) }
    private val bikeTexture: Texture by lazy { Texture(Gdx.files.local("assets/bike_line1.png")) }

    private var accumulator: Float = 0.0f
    private var deltaTimer: Float = 0.0f
    private var fps: Int = 0

    // TODO: Refactor to another file
    private val bikeWheelWidthPixels = 93f

    override fun show() {
        super.show()
    }

    override fun render(delta: Float) {
        //debugRenderer.render(world.physicsWorld, camera.combined)

        camera.position.set(Vector3(world.bike.body.position, 0f))
        camera.update()

        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            val vertices = world.vertices
            for (i in 0 until vertices.lastIndex) {
                // TODO: 3d :D
                it.line(vertices[i], vertices[i + 1])
            }
        }

        batch.projectionMatrix = camera.combined

        batch.use { b ->
            renderWheel(world.bike.frontWheel, b)
            renderWheel(world.bike.rearWheel, b)
            renderBike(world.bike, b)
        }

        textBatch.use {b ->
            font.draw(b, "Score: ${world.score.roundToInt()}", 20f, Gdx.graphics.height - 20f)
            font.draw(b, "FPS: $fps", 20f, Gdx.graphics.height - 40f)
            deltaTimer += delta
            if (deltaTimer > .1f) {
                fps = (1 / delta).toInt()
                deltaTimer = 0f
            }
        }

        checkInput()
        updatePhysics(delta)
    }

    override fun dispose() {
        batch.dispose()
        textBatch.dispose()
        wheelTexture.dispose()
        super.dispose()
    }

    private fun checkInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            world.bike.brake()
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            world.bike.accelerate()
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            world.bike.leanForward()
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            world.bike.leanBack()
        }
    }

    private fun renderBike(bike: Bike, batch: SpriteBatch) {
        val bikeWheelsWidthMeters: Float = bike.frontWheel.body.position.let { front ->
            val rear = bike.rearWheel.body.position
            sqrt((front.x - rear.x).pow(2) + (front.y - rear.y).pow(2))
        }
        val scale = bikeWheelsWidthMeters / bikeWheelWidthPixels
        val width = bikeTexture.width * scale
        val height = bikeTexture.height * scale
        batch.draw(
                bikeTexture,
                world.bike.body.position.x - (width / 2) + (width - bikeWheelsWidthMeters) - .1f, world.bike.body.position.y - (height / 2) - .15f,
                width / 2, height / 2, width, bikeTexture.height * scale, 1f, 1f,
                world.bike.body.angle * MathUtils.radiansToDegrees,
                0, 0, bikeTexture.width, bikeTexture.height, false, false
        )
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

    private fun updatePhysics(delta: Float) {
        accumulator += delta

        while (accumulator >= world.timeStep) {
            world.update()
            accumulator -= world.timeStep
        }
    }
}