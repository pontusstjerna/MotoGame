package screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import ktx.app.KtxScreen
import ktx.graphics.use
import model.GameWorld

// https://github.com/Quillraven/SimpleKtxGame/blob/01-app/core/src/com/libktx/game/screen/GameScreen.kt

class GameScreen : KtxScreen {

    private val world: GameWorld = GameWorld().apply { create() }

    private val batch: SpriteBatch by lazy { SpriteBatch() }
    private val debugRenderer = Box2DDebugRenderer()
    private val camera = OrthographicCamera().apply {
        // in meters
        val gameWidth = 50f
        val scale = gameWidth / Gdx.graphics.width

        setToOrtho(false, gameWidth, Gdx.graphics.height * scale)
    }
    private val shapeRenderer: ShapeRenderer = ShapeRenderer()
    private val img: Texture by lazy { Texture(Gdx.files.local("assets/bike_complete1.png")) }

    private var accumulator: Float = 0.0f

    override fun show() {
        super.show()
    }

    override fun render(delta: Float) {
        debugRenderer.render(world.physicsWorld, camera.combined)

        camera.update()
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            val position = world.dynamicBody.position
            it.rect(position.x - 1f, position.y - 1f, 2f, 2f)
            it.line(world.segment.from, world.segment.to)
        }

        //batch.projectionMatrix = camera.combined

        /*batch.use { b ->
            b.draw(img, x, 50f)
        }*/

        updatePhysics(delta)
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
        super.dispose()
    }

    private fun updatePhysics(delta: Float) {
        accumulator += delta

        while (accumulator >= world.timeStep) {
            world.update()
            accumulator -= world.timeStep
        }
    }
}