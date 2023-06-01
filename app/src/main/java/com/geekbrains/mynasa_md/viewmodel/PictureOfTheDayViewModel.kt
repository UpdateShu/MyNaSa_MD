package com.geekbrains.mynasa_md.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.mynasa_md.BuildConfig
import com.geekbrains.mynasa_md.model.PictureOfTheDayResponseData
import com.geekbrains.mynasa_md.model.RepositoryImpl
import com.geekbrains.mynasa_md.utils.Constants
import com.geekbrains.mynasa_md.utils.Constants.CORRUPTED_DATA
import com.geekbrains.mynasa_md.utils.Constants.PROJECT_ERROR
import com.geekbrains.mynasa_md.utils.Constants.SERVER_REQUEST_ERROR
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) :ViewModel()
{
    //получить liveData
    fun getLiveData(): LiveData<AppState> = liveData

    //послать серверный запрос
    fun sendRequest(progress: Int? = null, date: String) {
        liveData.postValue(AppState.Loading(progress))
        repositoryImpl.getPictureOfTheDayApi().getPictureByDate(Constants.NASA_API_KEY, date)
            .enqueue(callback)
    }

    private val callback = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            val serverResponse: PictureOfTheDayResponseData? = response.body()
            liveData.postValue(
                if (response.isSuccessful) {
                    checkDataResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_REQUEST_ERROR.toString()))
                }
            )
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            AppState.Error(Throwable(PROJECT_ERROR.toString()))
        }

        private fun checkDataResponse(serverResponse: PictureOfTheDayResponseData?): AppState
        {
            return if (serverResponse == null) {
                AppState.Error(Throwable(CORRUPTED_DATA.toString()))
            } else {
                AppState.Success(serverResponse)
            }
        }
    }
}