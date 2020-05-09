package se.nocroft.motogame.audio

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle

class Sound(fileHandle: FileHandle) {

    private val sound by lazy {
        Gdx.audio.newSound(fileHandle)
    }

    private var id: Long? = null

    fun play() {
        stop()
        id = sound.play()
    }

    fun playLoop() {
        stop()
        play()
        sound.setLooping(id!!, true)
    }

    fun setPitch(relativePitch: Float) {
        id?.let { sound.setPitch(it, relativePitch) }
    }

    fun stop() {
        id?.let {
            sound.stop(it)
            id = null
        }
    }

    fun dispose() {
        sound.dispose()
    }
}