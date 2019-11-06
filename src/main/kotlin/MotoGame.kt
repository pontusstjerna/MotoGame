import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class MotoGame : ApplicationAdapter() {
    lateinit private var batch: SpriteBatch
    lateinit private var img: Texture

    override fun create() {

        println(Gdx.files.localStoragePath)

        img = Texture(Gdx.files.local("assets/bike_complete1.png"))
        batch = SpriteBatch()

        super.create()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        batch.draw(img, 50f,50f)
        batch.end()

        super.render()
    }
}