import com.badlogic.gdx.Gdx
import ktx.app.KtxGame
import ktx.app.KtxScreen
import se.nocroft.motogame.screen.GameScreen
import se.nocroft.motogame.screen.MenuScreen
import se.nocroft.motogame.screen.MenuService

class MotoGame : KtxGame<KtxScreen>(), MenuService {

    override fun create() {
        addScreen(MenuScreen(this))
        addScreen(GameScreen(this))
        goToMenu()

        println(Gdx.files.localStoragePath)

        super.create()
    }

    override fun goToMenu() {
        setScreen<MenuScreen>()
    }

    override fun exit() {
        Gdx.app.exit()
    }

    override fun play() {
        setScreen<GameScreen>()
    }

    override fun dispose() {
        super.dispose()
    }
}