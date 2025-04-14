package vcmsa.projects.tamagotchi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

class FragmentSleep : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sleep, container, false)

        // Load the GIF
        val gifView = view.findViewById<ImageView>(R.id.gifView)
        Glide.with(this)
            .asGif()
            .load(R.drawable.sleep)
            .into(gifView)

        return view
    }
}