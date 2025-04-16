package vcmsa.projects.tamagotchi

import android.content.Context
import android.media.MediaPlayer

object SoundManager {

    private var mediaPlayer: MediaPlayer? = null

    fun playSound(context: Context, resId: Int) {
        // Stop any currently playing sound
        stopSound()

        // Initialize new MediaPlayer
        mediaPlayer = MediaPlayer.create(context, resId)
        mediaPlayer?.setOnCompletionListener {
            stopSound() // Release when done
        }
        mediaPlayer?.start()
    }

    fun stopSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
