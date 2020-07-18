import com.badlogic.gdx.Gdx
import ktx.app.KtxGame
import ktx.app.KtxScreen
import se.nocroft.motogame.screen.GameScreen
import se.nocroft.motogame.screen.HighScoreScreen
import se.nocroft.motogame.screen.MenuScreen
import se.nocroft.motogame.screen.MenuService

class MotoGame : KtxGame<KtxScreen>(), MenuService {

    override fun create() {
        addScreen(MenuScreen(this))
        addScreen(HighScoreScreen(this))
        addScreen(GameScreen(this))
        goToMenu()

        super.create()
    }

    override fun goToMenu() {
        setScreen<MenuScreen>()
    }

    override fun goToHighScores() {
        setScreen<HighScoreScreen>()
    }

    override fun exit() {
        Gdx.app.exit()
    }

    override fun play() {
        setScreen<GameScreen>()
    }
}