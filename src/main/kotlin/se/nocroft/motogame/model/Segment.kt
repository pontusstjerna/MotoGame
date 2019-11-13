package se.nocroft.motogame.model

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.EdgeShape
import com.badlogic.gdx.physics.box2d.World
import ktx.box2d.body

// https://box2d.org/manual.pdf page 18 - checkout ghost vertices

fun createSegment(from: Vector2, to: Vector2, ghostFrom: Vector2?, ghostTo: Vector2, world: World): Body {
    return world.body {
        edge(from, to) {
            density = 0.0f
            disposeOfShape = true
            shape = EdgeShape().apply {
                set(from, to)
                if (ghostFrom != null) {
                    setHasVertex0(true)
                    setVertex0(ghostFrom)
                }

                setHasVertex3(true)
                setVertex3(ghostTo)
            }
        }
    }
}