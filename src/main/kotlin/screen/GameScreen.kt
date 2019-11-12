package screen

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
import model.GameWorld
import model.Wheel
import kotlin.math.roundToInt

// https://github.com/Quillraven/SimpleKtxGame/blob/01-app/core/src/com/libktx/game/screen/GameScreen.kt

class GameScreen : KtxScreen {

    private val world: GameWorld = GameWorld()

    private val batch = SpriteBatch()
    private val textBatch = SpriteBatch()
    private val font = BitmapFont().apply {

    }
    private val debugRenderer = Box2DDebugRenderer()
    private val camera = OrthographicCamera().apply {
        // in meters
        val gameWidth = 20f
        val scale = gameWidth / Gdx.graphics.width

        setToOrtho(false, gameWidth, Gdx.graphics.height * scale)
    }
    private val shapeRenderer: ShapeRenderer = ShapeRenderer()
    private val wheelTexture: Texture by lazy { Texture(Gdx.files.local("assets/wheel4.png")) }
    private val bikeTexture: Texture by lazy { Texture(Gdx.files.local("assets/bike_complete1.png")) }

    private var accumulator: Float = 0.0f

    override fun show() {
        super.show()
    }

    override fun render(delta: Float) {
        debugRenderer.render(world.physicsWorld, camera.combined)

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
            // TODO:  draw bike

            renderWheel(world.bike.frontWheel, b)
            renderWheel(world.bike.rearWheel, b)
            /*b.draw(
                    bikeTexture,
                    world.bike.body.position.x - .95f, world.bike.body.position.y - .8f,
                    1f, .6f, 2f, 1.2f, 1f, 1f,
                    world.bike.body.angle * MathUtils.radiansToDegrees,
                    0, 0, this.bikeTexture.width, this.bikeTexture.height, false, false
            )*/
        }

        textBatch.use {b ->
            font.draw(b, "Score: " + world.bike.body.position.x.roundToInt(), 20f, Gdx.graphics.height - 20f)
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

    private fun renderWheel(wheel: Wheel, batch: SpriteBatch) {
        batch.draw(
                this.wheelTexture,
                wheel.body.position.x - wheel.radius,
                wheel.body.position.y - wheel.radius,
                wheel.radius,
                wheel.radius,
                wheel.radius * 2,
                wheel.radius * 2,
                1f,
                1f,
                wheel.body.angle * MathUtils.radiansToDegrees,
                0,
                0,
                this.wheelTexture.width,
                this.wheelTexture.height,
                false,
                false
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