package model

import com.badlogic.gdx.physics.box2d.World
import ktx.box2d.body
import ktx.box2d.createWorld
import ktx.box2d.earthGravity

class GameWorld {
    val physicsWorld: World = createWorld(gravity = earthGravity)

    val body = physicsWorld.body {
        box(width = 20f, height = 20f)
    }

    fun create() {

    }

}