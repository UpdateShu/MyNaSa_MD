package com.geekbrains.mynasa_md.model.response

import com.google.gson.annotations.SerializedName

data class MarsResponseData (
    val id: String,
    val sol: String,
    @SerializedName("img_src")
    val imgSrc: String,
    @SerializedName("earth_date")
    val earthDate: String
)