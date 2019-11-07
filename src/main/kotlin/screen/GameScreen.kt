package screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxScreen
import ktx.graphics.use

// https://github.com/Quillraven/SimpleKtxGame/blob/01-app/core/src/com/libktx/game/screen/GameScreen.kt

class GameScreen : KtxScreen {

    private val batch: SpriteBatch by lazy { SpriteBatch() }
    private val img: Texture by lazy { Texture(Gdx.files.local("assets/bike_complete1.png")) }

    private var x: Float = 0.0f

    override fun show() {
        super.show()
    }

    override fun render(delta: Float) {

        batch.use { b ->
            b.draw(img, x, 50f)
        }

        // 10 pixels per second YEAH
        x += Gdx.graphics.deltaTime * 10
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
        super.dispose()
    }
}