package com.geekbrains.mynasa_md.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyNote(
    val id: Int,
    var text: String,
    val date: String,
    val type: Int
) : Parcelable
