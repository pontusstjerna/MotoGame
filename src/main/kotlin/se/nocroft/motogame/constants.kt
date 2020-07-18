package se.nocroft.motogame

import com.badlogic.gdx.graphics.Color
import ktx.graphics.copy

var DEBUG = false
var FULLSCREEN = false

/* GAMEPLAY */
const val GRAVITY: Float = -5f //-9.81f
const val LEANING_TORQUE = 25f // 30f
// in meters
const val GAME_WIDTH = 20f
const val START_OFFSET = 5f

/* Padding */
const val PADDING_MEDIUM = 20f
const val PADDING_SMALL = 10f

/* Colors */
val TEXT_COLOR: Color = Color.WHITE
val TEXT_BUTTON_COLOR: Color = Color.WHITE
val TRACK_COLOR: Color = Color.WHITE//Color(132f/255f, 224f/255f, 215f/255f, .7f)
val PRIMARY_COLOR: Color = Color.WHITE
val HIGHSCORE_COLOR: Color = Color.WHITE

/* MISC */
const val HIGHSCORE_BORDER_THRESHOLD = 100f // Show border XX meters before new high score