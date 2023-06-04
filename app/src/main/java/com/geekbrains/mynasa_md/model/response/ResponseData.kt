package com.geekbrains.mynasa_md.model.response

data class ResponseData (
    val pictureOfTheDay : PictureOfTheDayResponseData? = null,
    val earth : List<EarthResponseData>? = null,
    val mars : PhotoMarsResponseData? = null,
    val solarFlares: List<SolarFlareResponseData>? = null
        )