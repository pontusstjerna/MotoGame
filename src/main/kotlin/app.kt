import com.badlogic.gdx.Application
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

/*
* Some tutorials I used:
* https://github.com/Quillraven/SimpleKtxGame/wiki/Application
* https://github.com/libgdx/libgdx/wiki/A-simple-game
*
* Box2D module:
* https://github.com/libktx/ktx/tree/master/box2d
* http://box2d.org/manual.pdf
*
* Scene2D module:
* https://github.com/libgdx/libgdx/wiki/Scene2d
*
* */

fun main() {
    //Example().run()

    // could be done with "apply" to avoid config-variable here!
    val config = LwjglApplicationConfiguration().also { config ->
        config.title = "MotoGame"
        config.width = 800
        config.height = 600
    }
    LwjglApplication(MotoGame(), config).logLevel = Application.LOG_DEBUG
}