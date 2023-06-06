package com.geekbrains.mynasa_md.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.ActivityMainBinding
import com.geekbrains.mynasa_md.model.theme.ThemeStorage
import com.geekbrains.mynasa_md.view.navigation.BottomNavigationFragment
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.ARG_CLICK_SAVE_THEME
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.KEY_CLICK_SAVE_THEME
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.TAG_MA

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val themeStorage : ThemeStorage = ThemeStorage(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        setTheme(themeStorage.getTheme().theme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.aBottomAppBar)

        //showFragment(SplashFragment.newInstance())
        supportFragmentManager.beginTransaction()
            .add(R.id.splash_view_container, SplashFragment.newInstance())
            .addToBackStack(null)
            .commit()
        Handler(mainLooper).postDelayed({
            supportFragmentManager.popBackStack()
            showFragment(BottomNavigationFragment.newInstance())
        }, 3000L)
        supportFragmentManager.setFragmentResultListener(KEY_CLICK_SAVE_THEME, this
        ) { _, result ->
            val keySaveTheme = result.getInt(ARG_CLICK_SAVE_THEME)
            themeStorage.saveTheme(keySaveTheme)
            this.recreate()
        }
        supportFragmentManager.setFragmentResultListener(KEY_URL, this){_, result ->
            val url = result.getString(ARG_URL)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bottom_app_bar, menu)
        super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_bab_setting -> {
                Log.d(TAG_MA, "menu Setting")
                //showFragment(PictureOfTheDayFragment.newInstance(), BACKSTACK)
            }
            R.id.menu_bab_fav -> {
                showFragment(BottomNavigationFragment.newInstance())
                Log.d(TAG_MA, "menu Bottom Navigation")
            }
            android.R.id.home -> {
                LessonsNavigationFragment(themeStorage.getTheme().key).show(supportFragmentManager, "")
                Log.d(TAG_MA, "menu Home")
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.lessons_view_container, fragment)
            .commit()
    }
}