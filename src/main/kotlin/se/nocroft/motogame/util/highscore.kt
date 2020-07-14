package se.nocroft.motogame.util

import com.badlogic.gdx.Gdx

fun getHighScores(): List<Int> {
    val prefs = Gdx.app.getPreferences("motogame")
    return prefs.getString("highscore", "")
            .split(",")
            .map { it.toIntOrNull() }
            .filterNotNull()
}

fun getHighScore(): Int {
    return getHighScores().firstOrNull() ?: 0
}

fun saveHighScore(score: Int) {
    if (score <= 0) {
        return
    }

    val prefs = Gdx.app.getPreferences("motogame")

    val highScores = getHighScores()
    val newHighScores: String = (highScores + score)
            .sortedDescending()
            .filter { it > 0 }
            .take(9)
            .joinToString(",") { it.toString() }

    prefs.putString("highscore", newHighScores)
    prefs.flush()
}