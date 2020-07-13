package se.nocroft.motogame.renderer.ui

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Value
import se.nocroft.motogame.PADDING_MEDIUM
import se.nocroft.motogame.screen.GameService
import kotlin.random.Random

class GameOverActor(private val gameService: GameService) : BaseMenuActor() {

    override val buttons = arrayOf(
            Button("Retry").apply {
                onPress {
                    gameService.reset()
                }
                selected = true
            },
            Button("Exit").apply {
                onPress {
                    gameService.exitToMenu()
                }
            }
    )

    private val gameOverTexts: Array<String> = arrayOf(
            "Whoops, crashy!",
            "Hey, you died now.",
            "Uh-oh!",
            "That was not supposed to happen...",
            "GAME OVER MOHAHA",
            "Whoops, you deaded!",
            "Ouch, that must've hurt.",
            "Better luck next time!",
            "Don't try that at home.",
            "Oh crap, who designed this game??",
            "Don't hug the ground.",
            "Maybe noclip is something for you?",
            "How about not diving into the ground.",
            "Sure hope you were wearing a helmet.",
            "So this is what it feels like to die.",
            "Something went terribly wrong."
    )

    private val gameOverLabel = Label("Whoops, you deaded. ")
    private val scoreLabel = Label("Your score: 0m")

    init {
        add(gameOverLabel)
        row()
        add(scoreLabel).pad(0f, 0f, PADDING_MEDIUM, 0f)
        row()
        add(buttons[0])
        row()
        add(buttons[1]).padBottom(Value.percentHeight(12.5f))
        setFillParent(true)
    }

    fun show(score: Int) {
        if (score > gameService.highscore) {
            gameOverLabel.setText("New high score!")
        } else {
            gameOverLabel.setText(gameOverTexts[Random.nextInt(gameOverTexts.size - 1)])
        }

        scoreLabel.setText("Your score: ${score}m")
        isVisible = true
    }
}