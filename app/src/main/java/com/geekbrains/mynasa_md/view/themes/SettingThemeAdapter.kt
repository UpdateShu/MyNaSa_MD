package com.geekbrains.mynasa_md.view.themes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.model.theme.Theme
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.DEFAULT_THEME
import com.geekbrains.mynasa_md.viewmodel.utils.EquilateralImageView
import com.google.android.material.textview.MaterialTextView

class SettingThemeAdapter(
    private val onClickTheme: SettingThemeFragment.OnClickTheme
) : RecyclerView.Adapter<SettingThemeAdapter.SettingThemeViewHolder>() {

    private var listTheme = Theme.values()
    private var keySaved: Int = DEFAULT_THEME

    fun getKeyThemeSaved(key: Int) {
        keySaved = key
    }


    inner class SettingThemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(theme: Theme) {
            val cardView = itemView.findViewById<CardView>(R.id.item_card_theme)
            cardView.setOnClickListener {
                onClickTheme?.saveClickTheme(theme)
            }
            val titleCardView = itemView.findViewById<MaterialTextView>(R.id.item_card_title)
            titleCardView.setText(theme.nameTheme)

            val checkSaveTheme = itemView.findViewById<EquilateralImageView>(R.id.item_card_image)

            if (theme.key == keySaved) {
                checkSaveTheme.isVisible = true
            } else {
                checkSaveTheme.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingThemeViewHolder =
        SettingThemeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_theme, parent, false) as View
        )

    override fun onBindViewHolder(holder: SettingThemeViewHolder, position: Int) {
        holder.bind(listTheme[position])
    }

    override fun getItemCount(): Int = listTheme.size


}