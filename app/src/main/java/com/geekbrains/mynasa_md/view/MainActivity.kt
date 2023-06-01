package com.geekbrains.mynasa_md.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.ActivityMainBinding
import com.geekbrains.mynasa_md.model.theme.ThemeStorage
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.ARG_CLICK_SAVE_THEME
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.BACKSTACK
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.KEY_CLICK_SAVE_THEME
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.NO_BACKSTACK
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.TAG_MA
import com.geekbrains.mynasa_md.view.notes.NotesFragment
import com.google.android.material.bottomappbar.BottomAppBar

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
        supportFragmentManager.setFragmentResultListener(KEY_CLICK_SAVE_THEME, this
        ) { _, result ->
            val keySaveTheme = result.getInt(ARG_CLICK_SAVE_THEME)
            themeStorage.saveTheme(keySaveTheme)
            this.recreate()
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
                Log.d(TAG_MA, "menu Favourite")
            }
            android.R.id.home -> {
                LessonsNavigationFragment(themeStorage.getTheme().key).show(supportFragmentManager, "")
                Log.d(TAG_MA, "menu Home")
            }

        }
        return super.onOptionsItemSelected(item)
    }
}