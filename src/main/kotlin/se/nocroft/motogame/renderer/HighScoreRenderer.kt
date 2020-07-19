package se.nocroft.motogame.renderer

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import ktx.graphics.use
import se.nocroft.motogame.*
import se.nocroft.motogame.screen.GameService
import kotlin.math.max

class HighScoreRenderer(private val gameService: GameService) {

    private val MIN_HIGHSCORE_VALUE = 30

    private val shapeRenderer: ShapeRenderer = ShapeRenderer().apply {
        color = HIGHSCORE_COLOR
    }

    private val orthoCamera = OrthographicCamera().apply {
        viewportWidth = 10f
        viewportHeight = 10f
    }

    fun render(projectionMatrix: Matrix4) {

        shapeRenderer.projectionMatrix = projectionMatrix
        val highScore = gameService.highScore
        val distToHighScore = highScore - gameService.distance - START_OFFSET
        val alpha = 1 - max(distToHighScore / (GAME_WIDTH / 2), 0f)
        shapeRenderer.color.a = alpha
        if (highScore < MIN_HIGHSCORE_VALUE || distToHighScore <= 0) return

        renderLine(distToHighScore = distToHighScore, highScore = highScore.toFloat())
        renderBorder(distToHighScore)
    }

    private fun renderLine(distToHighScore: Float, highScore: Float) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) { draw ->
            draw.line(highScore, -1000f, highScore, 1000f)
        }
    }

    private fun renderBorder(distToHighScore: Float) {
        shapeRenderer.projectionMatrix = orthoCamera.combined
        orthoCamera.zoom = 0f
        orthoCamera.position.z = 0f

        // Meters before borders show
        val alpha = max(.5f - distToHighScore / HIGHSCORE_BORDER_THRESHOLD, 0f)
        val color = HIGHSCORE_COLOR.cpy().apply { a = alpha }
        val transparent = Color(0f, 0f, 0f, 0f)
        val borderWidth = HIGHSCORE_BORDER_WIDTH
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) { draw ->
            draw.rect(-1f, -1f, 2f, borderWidth, color, color, transparent, transparent)
            draw.rect(-1f, -1f, borderWidth, 2f, color, transparent, transparent, color)
            draw.rect(-1f, 1f - borderWidth, 2f, borderWidth, transparent, transparent, color, color)
            draw.rect(1f - borderWidth, -1f, borderWidth, 2f, transparent, color, color, transparent)
        }
    }
}