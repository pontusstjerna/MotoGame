import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxGame
import ktx.app.KtxScreen

class MotoGame : ApplicationAdapter() {
    lateinit private var batch: SpriteBatch
    lateinit private var img: Texture

    override fun create() {

        img = Texture(Gdx.files.internal("bike_complete1.png"))
        batch = SpriteBatch()

        super.create()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        super.render()
    }
}