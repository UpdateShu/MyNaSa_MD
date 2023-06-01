package com.geekbrains.mynasa_md.view

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import com.geekbrains.mynasa_md.R

class SpannableManager(val context: Context) {

    fun setSpannableTitle(spannableStringBuilder: SpannableStringBuilder) {
        val request = FontRequest("com.google.android.gms.fonts",
            "com.google.android.gms","Macondo",
            R.array.com_google_android_gms_fonts_certs)

        val callback = object : FontsContractCompat.FontRequestCallback(){
            @RequiresApi(Build.VERSION_CODES.P)
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                typeface?.let{
                    spannableStringBuilder.setSpan(TypefaceSpan(it),
                        0, spannableStringBuilder.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                }
                super.onTypefaceRetrieved(typeface)
            }
        }
        FontsContractCompat.requestFont(context, request, callback,
            Handler(Looper.getMainLooper()))
    }

    fun setSpannableDescription(spannableStringBuilder: SpannableStringBuilder) {

        setLetterForegroundColor(spannableStringBuilder, 't', R.color.material_red_600)
        setLetterForegroundColor(spannableStringBuilder, 'o', R.color.material_green_500)

        setWordTypeface(spannableStringBuilder, R.font.azeret, R.color.material_purple_200)
    }

    fun setWordTypeface(spannableStringBuilder: SpannableStringBuilder, fontResId: Int, colorId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val text = spannableStringBuilder.toString()
            for (i in text.indices) {
                if (text[i].isUpperCase()) {
                    spannableStringBuilder.setSpan(
                        BackgroundColorSpan(ContextCompat.getColor(context, colorId)),
                            i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                    val endWordIndex = spannableStringBuilder.substring(i).indexOf(' ')
                    if (endWordIndex != -1 && endWordIndex < text.length) {
                        //val word = text.substring(i, i + endWordIndex)
                        spannableStringBuilder.setSpan(TypefaceSpan(context.resources.getFont(fontResId)),
                            i, i + endWordIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                    }
                }
            }
        }
    }

    fun setLetterForegroundColor(spannableStringBuilder: SpannableStringBuilder, letter: Char, colorId: Int)
    {
        val text = spannableStringBuilder.toString()
        for (i in text.indices) {
            if (text[i] == letter) {
                spannableStringBuilder.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, colorId)),
                    i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    fun String.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> =
        (if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr))
            .findAll(this).map { it.range.first }.toList()
}


