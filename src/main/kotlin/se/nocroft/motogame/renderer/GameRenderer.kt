package se.nocroft.motogame.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
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
import se.nocroft.motogame.DEBUG
import se.nocroft.motogame.GAME_WIDTH
import se.nocroft.motogame.START_OFFSET
import se.nocroft.motogame.TRACK_COLOR
import se.nocroft.motogame.model.Bike
import se.nocroft.motogame.model.GameWorld
import se.nocroft.motogame.model.Rider
import se.nocroft.motogame.model.Wheel
import se.nocroft.motogame.screen.GameService
import kotlin.math.*

class GameRenderer(private val world: GameWorld, private val gameService: GameService) {

    private val batch = SpriteBatch()
    private val textBatch = SpriteBatch()
    private val font = BitmapFont()
    private val debugRenderer = Box2DDebugRenderer()

    private val camera = PerspectiveCamera().apply {
        val scale = GAME_WIDTH / Gdx.graphics.width
        viewportWidth = GAME_WIDTH
        viewportHeight = Gdx.graphics.height * scale
        fieldOfView = 57f
        rotate(-10f, 1f, 0f, 0f)
    }

    private val shapeRenderer: ShapeRenderer = ShapeRenderer().apply {
        color = TRACK_COLOR
    }

    private val highScoreRenderer = HighScoreRenderer(gameService)

    private val wheelTexture: Texture by lazy {
        Texture(Gdx.files.local("assets/wheel5.png"), true).apply {
            setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }
    private val bikeTexture: Texture by lazy {
        Texture(Gdx.files.local("assets/bike_line_3.png"), true).apply {
            setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        }
    }
    private val ridersTexture: RiderTexture by lazy {
        RiderTexture(Gdx.files.local("assets/riders.png"), true).apply {
            setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        }
    }
    private val bikeWheelWidthPixels = 106f

    private val trackWidth = 1f
    private var zoom: Float = 10f
    private val defaultZoom: Float = if (DEBUG) 5f else 8f

    fun resize(width: Int, height: Int) {
        val scale = GAME_WIDTH / width
        camera.viewportWidth = GAME_WIDTH
        camera.viewportHeight = Gdx.graphics.height * scale
    }

    fun render(delta: Float) {

        Gdx.gl.glClearColor(.14f, .14f, .14f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT or (if (Gdx.graphics.bufferFormat.coverageSampling) GL20.GL_COVERAGE_BUFFER_BIT_NV else 0))
        Gdx.gl.glEnable(GL20.GL_BLEND)

        camera.position.set(
                world.bike.body.position.x + (distanceBetweenWheelsMeters() / 2f),
                world.bike.body.position.y + 3,
                getZoom())
        camera.update()

        renderTerrain(world.vertices)
        highScoreRenderer.render(camera.combined)

        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.projectionMatrix = camera.combined
        batch.use { b ->
            renderRider(world.bike, b)
            renderWheel(world.bike.frontWheel, b)
            renderWheel(world.bike.rearWheel, b)
            renderBike(world.bike, b)
        }

        ridersTexture.update(delta = delta, rider = world.bike.rider)

        if (DEBUG) {
            debugRenderer.render(world.physicsWorld, camera.combined)
        }
    }

    fun dispose() {
        batch.dispose()
        textBatch.dispose()
        wheelTexture.dispose()
    }

    private fun getZoom(): Float {
        val goal = when {
            gameService.isPaused || gameService.isDead -> 7f
            else -> defaultZoom + (world.bike.body.linearVelocity.len() * 0.5f)
        }

        val zoomSpeed = 0.02f
        zoom += (goal - zoom) * zoomSpeed
        return zoom
    }

    private fun renderTerrain(vertices: List<Vector2>) {
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            for (i in 0 until vertices.lastIndex) {
                val fst = vertices[i].toImmutable()
                val snd = vertices[i + 1].toImmutable()

                // EXPERiMENTAL
                val distToHighScore = (snd.x - world.bike.body.position.x).absoluteValue
                val alpha = max(1 - distToHighScore / (GAME_WIDTH / 2), 0f)
                shapeRenderer.color.a = alpha

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
                bike.body.position.x - (width / 2), bike.body.position.y - (height / 2),
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

    private fun renderRider(bike: Bike, batch: SpriteBatch) {
        val scale = bike.rider.height / ridersTexture.frameHeight
        val width = ridersTexture.frameWidth * scale
        val height = ridersTexture.frameHeight * scale
        val bikeWidth = bikeTexture.width * scale
        val bikeHeight = bike.height

        batch.draw(
                ridersTexture,
                bike.body.position.x - (bikeWidth / 2),
                bike.body.position.y - (bikeHeight / 2),
                width / 2, bikeHeight / 2,
                width, height, 1f, 1f,
                bike.body.angle * MathUtils.radiansToDegrees,
                ridersTexture.getOffsetX(), ridersTexture.getOffsetY(),
                ridersTexture.frameWidth,
                ridersTexture.frameHeight,
                false,
                false
        )

    }
}