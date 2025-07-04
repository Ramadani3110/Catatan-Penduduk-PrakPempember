package com.rams.catatanpenduduk.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.rams.catatanpenduduk.R
import com.rams.catatanpenduduk.databinding.ActivityMainBinding
import com.rams.catatanpenduduk.utils.FabHandler

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcMain) as NavHostFragment
        navController = navHostFragment.navController

        binding.mainBottomBar.onItemSelected = { index ->
            when (index) {
                0 -> {
                    if (navController.currentDestination?.id != R.id.desaFragment) {
                        navController.navigate(R.id.desaFragment)
                    }
                }
                1 -> {
                    if (navController.currentDestination?.id != R.id.kecamatanFragment) {
                        navController.navigate(R.id.kecamatanFragment)
                    }
                }
                2 -> {
                    if (navController.currentDestination?.id != R.id.pendudukFragment) {
                        navController.navigate(R.id.pendudukFragment)
                    }
                }
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.root.post {
                val currentFragment = supportFragmentManager
                    .findFragmentById(R.id.fcMain)
                    ?.childFragmentManager
                    ?.primaryNavigationFragment

                if (currentFragment is FabHandler && currentFragment.onShow()) {
                    binding.fabMain.show()
                } else {
                    binding.fabMain.hide()
                }

                val activeIndex = when (destination.id) {
                    R.id.desaFragment -> 0
                    R.id.kecamatanFragment -> 1
                    R.id.pendudukFragment -> 2
                    else -> -1
                }

                if (activeIndex != -1 && binding.mainBottomBar.itemActiveIndex != activeIndex) {
                    binding.mainBottomBar.itemActiveIndex = activeIndex
                }
            }
        }

        binding.fabMain.setOnClickListener {
            val currentFragment = supportFragmentManager
                .findFragmentById(R.id.fcMain)
                ?.childFragmentManager
                ?.fragments
                ?.firstOrNull()

            if(currentFragment is FabHandler){
                currentFragment.onFabClick()
            }
        }

    }

    @Suppress("DEPRECATION")
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        val currentDestination = navController.currentDestination?.id

        if (currentDestination == R.id.desaFragment) {
            super.onBackPressed()
        } else {
            finish()
        }
    }
}