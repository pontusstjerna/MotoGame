import com.badlogic.gdx.Gdx
import ktx.app.KtxGame
import ktx.app.KtxScreen
import se.nocroft.motogame.screen.GameScreen

class MotoGame : KtxGame<KtxScreen>() {

    override fun create() {

        addScreen(GameScreen())
        setScreen<GameScreen>()

        println(Gdx.files.localStoragePath)

        super.create()
    }

    override fun dispose() {
        super.dispose()
    }
}