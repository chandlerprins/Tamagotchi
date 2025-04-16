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


class FragmentPee : Fragment() {

    private lateinit var happinessBar: ProgressBar
    private lateinit var healthBar: ProgressBar
    private lateinit var energyBar: ProgressBar
    private lateinit var gifView: ImageView

    // Pet stats
    private var happiness = 100
    private var health = 100
    private var energy = 100

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var statDecayRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pee, container, false)

        happinessBar = view.findViewById(R.id.happinessBar)
        healthBar = view.findViewById(R.id.healthBar)
        energyBar = view.findViewById(R.id.energyBar)
        gifView = view.findViewById(R.id.gifView)

        // Load peeing GIF
        Glide.with(this)
            .asGif()
            .load(R.drawable.pee)
            .into(gifView)

        // On click: relieve pet = +happiness, +health
        gifView.setOnClickListener {
            relievePet()
        }

        updateProgressBars()
        startStatDecay()

        // Play background eat sound when entering the fragment
        SoundManager.playSound(requireContext(), R.raw.pee_sound)

        return view
    }

    private fun relievePet() {
        happiness = (happiness + 10).coerceAtMost(100)
        health = (health + 5).coerceAtMost(100)
        energy = (energy - 5).coerceAtLeast(0)

        updateProgressBars()
        Toast.makeText(requireContext(), "Phew... relief! 💧", Toast.LENGTH_SHORT).show()
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
                health = (health - 2).coerceAtLeast(0)
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
        SoundManager.stopSound()
    }
}
