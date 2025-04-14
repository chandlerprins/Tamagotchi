package vcmsa.projects.tamagotchi

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class FragmentEat : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_eat, container, false)

        // Load the GIF
        val gifView = view.findViewById<ImageView>(R.id.gifView)
        Glide.with(this)
            .asGif()
            .load(R.drawable.eat)
            .into(gifView)

       return view
    }
}