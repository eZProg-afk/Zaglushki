package com.betnmoney.betsplayer.ui.main

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.betnmoney.betsplayer.R
import com.betnmoney.betsplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityMainBinding>()
    private var appBarConfiguration: AppBarConfiguration? = null
    private var navController: NavController? = null
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.mainFragmentContainerView) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(binding.appBar.toolbar)

        val imageLoader = ImageLoader.Builder(this)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }.build()
        Coil.setImageLoader(imageLoader)

        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.betListFragment,
                R.id.betHistoryFragment,
                R.id.settingsFragment,
                R.id.infoFragment,
                R.id.rateUsFragment
            ), binding.drawerLayout
        )

        setupActionBarWithNavController(checkNotNull(navController), checkNotNull(appBarConfiguration))
        binding.navigationView.setupWithNavController(checkNotNull(navController))

        binding.drawerLayout.bringToFront()
        binding.drawerLayout.requestFocus()

        binding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.balanceTextView).text =
            getString(R.string.balance_format, USER_BALANCE)
    }

    override fun onResume() {
        super.onResume()
        binding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.balanceTextView).text =
            getString(R.string.balance_format, USER_BALANCE)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController!!.navigateUp(appBarConfiguration!!) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        /* no-op */
    }

    companion object {
        var USER_BALANCE = 5000
    }
}