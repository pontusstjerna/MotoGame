package screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import ktx.app.KtxScreen
import ktx.graphics.use
import model.GameWorld

// https://github.com/Quillraven/SimpleKtxGame/blob/01-app/core/src/com/libktx/game/screen/GameScreen.kt

class GameScreen : KtxScreen {

    private val world: GameWorld by lazy { GameWorld().apply { create() } }

    private val batch: SpriteBatch by lazy { SpriteBatch() }
    private val shapeRenderer: ShapeRenderer by lazy { ShapeRenderer() }
    private val img: Texture by lazy { Texture(Gdx.files.local("assets/bike_complete1.png")) }

    private val scale = 10f

    private var accumulator: Float = 0.0f

    override fun show() {
        super.show()
    }

    override fun render(delta: Float) {

        accumulator += delta

        while (accumulator <= world.timeStep) {
            world.update()
            accumulator -= world.timeStep
        }

        /*batch.use { b ->
            b.draw(img, x, 50f)
        }*/

        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            val position = world.dynamicBody.position
            it.rect(position.x * scale, position.y * scale, 50f, 50f)
        }
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
        super.dispose()
    }
}