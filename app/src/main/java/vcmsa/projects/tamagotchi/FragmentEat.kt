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

class FragmentEat : Fragment() {

    private lateinit var happinessBar: ProgressBar
    private lateinit var healthBar: ProgressBar
    private lateinit var energyBar: ProgressBar
    private lateinit var gifView: ImageView

    // Pet stats
    private var happiness = 100
    private var health = 100
    private var energy = 100

    // Stat decay handler
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var statDecayRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_eat, container, false)

        // Link UI elements
        happinessBar = view.findViewById(R.id.happinessBar)
        healthBar = view.findViewById(R.id.healthBar)
        energyBar = view.findViewById(R.id.energyBar)
        gifView = view.findViewById(R.id.gifView)

        // Load the GIF using Glide
        Glide.with(this)
            .asGif()
            .load(R.drawable.eat)
            .into(gifView)

        // Tap on the GIF to feed
        gifView.setOnClickListener {
            feedPet()
        }

        updateProgressBars()
        startStatDecay()

        // Play background eat sound when entering the fragment
        SoundManager.playSound(requireContext(), R.raw.eat_sound)

        return view
    }


    private fun feedPet() {
        // Update stats
        health = (health + 15).coerceAtMost(100)
        energy = (energy - 5).coerceAtLeast(0)
        happiness = (happiness + 5).coerceAtMost(100)

        updateProgressBars()

        Toast.makeText(requireContext(), "Yum! 🐾", Toast.LENGTH_SHORT).show()
    }

    private fun updateProgressBars() {
        happinessBar.progress = happiness
        healthBar.progress = health
        energyBar.progress = energy
    }

    private fun startStatDecay() {
        statDecayRunnable = object : Runnable {
            override fun run() {
                // Reduce stats slightly
                happiness = (happiness - 2).coerceAtLeast(0)
                health = (health - 1).coerceAtLeast(0)
                energy = (energy - 1).coerceAtLeast(0)

                updateProgressBars()

                if (happiness == 0 || health == 0 || energy == 0) {
                    Toast.makeText(requireContext(), "Game Over! 😢", Toast.LENGTH_LONG).show()
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
        SoundManager.stopSound() // Stop sound on exit
    }
}
