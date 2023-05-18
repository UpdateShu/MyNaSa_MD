package com.geekbrains.mynasa_md.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureByDate(
        @Query("api_key") apiKey:String,
        @Query("date") date: String
    ): Call<PictureOfTheDayResponseData>
}