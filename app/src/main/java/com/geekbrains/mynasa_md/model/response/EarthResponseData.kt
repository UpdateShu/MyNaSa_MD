package com.geekbrains.mynasa_md.model.response

data class EarthResponseData(
    val identifier: String,
    val caption: String,
    val image: String,
    val version: String,
    val date: String
) {
    fun getImageUrl() : String = "https://epic.gsfc.nasa.gov/epic-archive/jpg/" + image + ".jpg"
}