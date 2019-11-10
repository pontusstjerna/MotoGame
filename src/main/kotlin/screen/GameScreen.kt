package screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import ktx.app.KtxScreen
import ktx.graphics.use
import model.GameWorld
import model.Wheel

// https://github.com/Quillraven/SimpleKtxGame/blob/01-app/core/src/com/libktx/game/screen/GameScreen.kt

class GameScreen : KtxScreen {

    private val world: GameWorld = GameWorld()

    private val batch: SpriteBatch by lazy { SpriteBatch() }
    private val debugRenderer = Box2DDebugRenderer()
    private val camera = OrthographicCamera().apply {
        // in meters
        val gameWidth = 20f
        val scale = gameWidth / Gdx.graphics.width

        setToOrtho(false, gameWidth, Gdx.graphics.height * scale)
    }
    private val shapeRenderer: ShapeRenderer = ShapeRenderer()
    private val wheelTexture: Texture by lazy { Texture(Gdx.files.local("assets/wheel4.png")) }
    private val bikeTexture: Texture by lazy { Texture(Gdx.files.local("assets/bike_complete2.png")) }

    private var accumulator: Float = 0.0f

    override fun show() {
        super.show()
    }

    override fun render(delta: Float) {
        debugRenderer.render(world.physicsWorld, camera.combined)

        camera.update()
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            world.segments.forEach { segment ->
                it.line(segment.from, segment.to)
            }
        }

        batch.projectionMatrix = camera.combined

        batch.use { b ->
            // TODO:  draw bike

            renderWheel(world.bike.frontWheel, b)
            renderWheel(world.bike.rearWheel, b)
        }

        updatePhysics(delta)
    }

    override fun dispose() {
        batch.dispose()
        wheelTexture.dispose()
        super.dispose()
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