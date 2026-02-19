package com.example.inshorts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.inshorts.databinding.ActivityMainBinding

/**
 * Single Activity: hosts NavHostFragment (Home, Search, Saved, Detail) and bottom navigation.
 * Handles dummy deeplink (inshorts://movie/{id}) so opening the link navigates to movie detail.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.main.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        handleDeeplink(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeeplink(intent)
    }

    /**
     * If the app was opened via inshorts://movie/{id}, navigate to the detail fragment with that movie id.
     * Post navigation so the NavHost is ready (e.g. on cold start from deeplink).
     */
    private fun handleDeeplink(intent: Intent?) {
        val uri: Uri = intent?.data ?: return
        if (uri.scheme != "inshorts" || uri.host != "movie") return
        val pathSegments = uri.pathSegments ?: return
        if (pathSegments.isEmpty()) return
        val id = pathSegments[0].toIntOrNull() ?: return
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment ?: return
        Log.i(TAG, "Deeplink handled, navigating to movie id=$id")
        val bundle = Bundle().apply { putInt("movieId", id) }
        navHost.view?.post { navHost.navController.navigate(R.id.detailFragment, bundle) }
            ?: navHost.navController.navigate(R.id.detailFragment, bundle)
    }

    companion object {
        private const val TAG = "Inshorts/MainActivity"
    }
}
