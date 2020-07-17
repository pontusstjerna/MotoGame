package se.nocroft.motogame.renderer

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import ktx.graphics.use
import se.nocroft.motogame.GAME_WIDTH
import se.nocroft.motogame.HIGHSCORE_COLOR
import se.nocroft.motogame.START_OFFSET
import se.nocroft.motogame.screen.GameService
import kotlin.math.max

class HighScoreRenderer(private val gameService: GameService) {

    private val MIN_HIGHSCORE_VALUE = 30

    private val shapeRenderer: ShapeRenderer = ShapeRenderer().apply {
        color = HIGHSCORE_COLOR
    }

    fun render(projectionMatrix: Matrix4) {

        shapeRenderer.projectionMatrix = projectionMatrix
        val highScore = gameService.highScore
        val distToHighScore = highScore - gameService.distance - START_OFFSET
        val alpha = 1 - max(distToHighScore / (GAME_WIDTH / 2), 0f)
        shapeRenderer.color.a = alpha
        if (highScore < MIN_HIGHSCORE_VALUE || distToHighScore <= 0) return

        shapeRenderer.use(ShapeRenderer.ShapeType.Line) { draw ->
            draw.line(highScore.toFloat(), -1000f, highScore.toFloat(), 1000f)
        }
    }
}