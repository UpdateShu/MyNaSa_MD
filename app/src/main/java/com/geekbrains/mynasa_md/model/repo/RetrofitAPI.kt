package com.geekbrains.mynasa_md.model.repo

import android.app.BackgroundServiceStartNotAllowedException
import com.geekbrains.mynasa_md.model.response.EarthResponseData
import com.geekbrains.mynasa_md.model.response.MarsResponseData
import com.geekbrains.mynasa_md.model.response.PhotoMarsResponseData
import com.geekbrains.mynasa_md.model.response.PictureOfTheDayResponseData
import com.geekbrains.mynasa_md.model.response.SolarFlareResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey:String,
                         @Query("date") date: String): Call<PictureOfTheDayResponseData>

    @GET("EPIC/api/natural/images")
    fun getEarth(@Query("api_key") apiKey:String): Call<List<EarthResponseData>>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getMars(@Query("sol") sol: Int,
                @Query("api_key") apiKey:String): Call<PhotoMarsResponseData>

    @GET("DONKI/FLR")
    fun getSolarFlare(@Query("api_key") apiKey: String,
                      @Query("startDate") startDate: String,
                      @Query("endDate") endDate: String): Call<List<SolarFlareResponseData>>
}