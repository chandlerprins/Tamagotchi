package vcmsa.projects.tamagotchi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

class FragmentWash : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wash, container, false)

        // Load the GIF
        val gifView = view.findViewById<ImageView>(R.id.gifView)
        Glide.with(this)
            .asGif()
            .load(R.drawable.wash)
            .into(gifView)

        return view
    }
}