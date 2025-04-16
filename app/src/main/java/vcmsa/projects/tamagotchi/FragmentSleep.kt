package vcmsa.projects.tamagotchi


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

import vcmsa.projects.tamagotchi.SoundManager


class FragmentSleep : Fragment() {

    private lateinit var happinessBar: ProgressBar
    private lateinit var healthBar: ProgressBar
    private lateinit var energyBar: ProgressBar
    private lateinit var gifView: ImageView

    private var happiness = 100
    private var health = 100
    private var energy = 100

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var statDecayRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sleep, container, false)

        happinessBar = view.findViewById(R.id.happinessBar)
        healthBar = view.findViewById(R.id.healthBar)
        energyBar = view.findViewById(R.id.energyBar)
        gifView = view.findViewById(R.id.gifView)

        Glide.with(this)
            .asGif()
            .load(R.drawable.sleep)
            .into(gifView)

        gifView.setOnClickListener {
            putPetToSleep()
        }

        updateProgressBars()
        startStatDecay()

        // Play background eat sound when entering the fragment
        SoundManager.playSound(requireContext(), R.raw.sleep_sound)

        return view
    }

    private fun putPetToSleep() {
        energy = (energy + 20).coerceAtMost(100)
        happiness = (happiness - 5).coerceAtLeast(0)
        health = (health + 5).coerceAtMost(100)

        updateProgressBars()
        Toast.makeText(requireContext(), "Zzz... 💤", Toast.LENGTH_SHORT).show()
    }

    private fun updateProgressBars() {
        happinessBar.progress = happiness
        healthBar.progress = health
        energyBar.progress = energy
    }

    private fun startStatDecay() {
        statDecayRunnable = object : Runnable {
            override fun run() {
                happiness = (happiness - 1).coerceAtLeast(0)
                health = (health - 1).coerceAtLeast(0)
                energy = (energy - 2).coerceAtLeast(0)

                updateProgressBars()

                if (happiness == 0 || health == 0 || energy == 0) {
                    Toast.makeText(requireContext(), "Game Over! 😴", Toast.LENGTH_LONG).show()
                    handler.removeCallbacks(statDecayRunnable)
                } else {
                    handler.postDelayed(this, 5000)
                }
            }
        }
        handler.postDelayed(statDecayRunnable, 5000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(statDecayRunnable)
        SoundManager.stopSound()
    }
}
