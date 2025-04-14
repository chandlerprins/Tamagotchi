package vcmsa.projects.tamagotchi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(FragmentEat())

        bottomNav = findViewById(R.id.NavBar)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_eat -> loadFragment(FragmentEat())
                R.id.nav_sleep -> loadFragment(FragmentSleep())
                R.id.nav_wash -> loadFragment(FragmentWash())
                R.id.nav_pee -> loadFragment(FragmentPee())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }
}
