package cl.gringraz.marvelcatalog

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cl.gringraz.flagboard_android.Flagboard
import cl.gringraz.marvelcatalog.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        createNavigationMenu(navView)

        val menuSet = buildSet {
            add(R.id.navigation_home)
            add(R.id.navigation_favorites)
            add(R.id.navigation_search)
        }

        val appBarConfiguration = AppBarConfiguration(menuSet)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun createNavigationMenu(navView: BottomNavigationView) {
        val menu = navView.menu
        if (Flagboard.getBoolean("favorites")) {
            menu.add(Menu.NONE, R.id.navigation_favorites, Menu.NONE, "Favorites")
                .setIcon(R.drawable.ic_favorites_star_24dp)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (BuildConfig.DEBUG) menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.flagboard) {
            Flagboard.open(this)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_activity_main).navigateUp()
    }
}