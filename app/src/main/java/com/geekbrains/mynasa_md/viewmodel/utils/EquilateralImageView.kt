package com.geekbrains.mynasa_md.viewmodel.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

//Анотация для работы на java, для котлина не нужен
class EquilateralImageView @JvmOverloads constructor (
    context: Context,
    attrSet: AttributeSet? = null,
    defaultStyle: Int = 0
) : AppCompatImageView(context, attrSet, defaultStyle){

    // todo Переопределение метода размера ImageView, super.onMeasure(ширина, высота)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec )
    }
}