import com.badlogic.gdx.Application
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

/*
* Some tutorials I used:
* https://github.com/Quillraven/SimpleKtxGame/wiki/Application
* https://github.com/libgdx/libgdx/wiki/A-simple-game
* */

fun main() {
    //Example().run()

    // could be done with "apply" to avoid config-variable here!
    val config = LwjglApplicationConfiguration().also { config ->
        config.title = "MotoGame"
        config.width = 800
        config.height = 600
    }
    LwjglApplication(MotoGame(), config).logLevel = Application.LOG_DEBUG;
}