package com.geekbrains.mynasa_md.model.theme

import android.content.Context
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.DEFAULT_THEME

class ThemeStorage(private val context: Context) {
    companion object {
        private val ARG_THEME = "ARG_THEME"
    }

    fun saveTheme(keyTheme: Int) {
        context.getSharedPreferences("Theme", Context.MODE_PRIVATE)?.let {
            it
                .edit()
                .putInt(ARG_THEME, keyTheme)
                .apply()
        }
    }

    fun getTheme(): Theme {
        context.getSharedPreferences("Theme", Context.MODE_PRIVATE)
            ?.let { sp ->
                sp.getInt(ARG_THEME, DEFAULT_THEME)
                    .let { key ->
                        enumValues<Theme>().forEach { theme ->
                            if (key == theme.key) {
                                return theme
                            }
                        }
                    }
            }
        return Theme.CYAN_THEME
    }
}