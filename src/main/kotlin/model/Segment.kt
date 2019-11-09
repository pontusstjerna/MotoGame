package model

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import ktx.box2d.body
import ktx.box2d.edge

class Segment(from: Vector2, to: Vector2, world: World) {

    val body: Body = world.body {
        edge(from, to) {
            density = 0.0f
            disposeOfShape = true
        }
    }.apply {
        edge(from = from, to = to)
    }
}