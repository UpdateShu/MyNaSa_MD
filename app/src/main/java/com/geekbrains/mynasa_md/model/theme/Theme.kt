package com.geekbrains.mynasa_md.model.theme

import androidx.annotation.StringRes
import com.geekbrains.mynasa_md.R

enum class Theme (
    val theme: Int,
    @StringRes val nameTheme:Int,
    val key: Int
) {
    CYAN_THEME(R.style.myThemeCyan, R.string.cyan_theme, 1),
    PINK_THEME(R.style.myThemePink, R.string.pink_theme, 2),
    PURPLE_THEME(R.style.myThemePurple, R.string.purple_theme, 3)
}