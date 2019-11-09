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

    private val world: GameWorld by lazy { GameWorld().apply { create() } }

    private val batch: SpriteBatch by lazy { SpriteBatch() }
    private val debugRenderer = Box2DDebugRenderer()
    private val camera = OrthographicCamera().apply {
        setToOrtho(false, 800f, 600f);
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
            it.rect(position.x - 10f, position.y - 10f, 20f, 20f)
        }

        //camera.update()
        batch.projectionMatrix = camera.combined
        accumulator += delta

        while (accumulator >= world.timeStep) {
            world.update()
            accumulator -= world.timeStep
        }

        /*batch.use { b ->
            b.draw(img, x, 50f)
        }*/
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
        super.dispose()
    }
}