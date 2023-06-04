package com.geekbrains.mynasa_md.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.mynasa_md.model.repo.RepositoryImpl
import com.geekbrains.mynasa_md.model.response.EarthResponseData
import com.geekbrains.mynasa_md.model.response.ResponseData
import com.geekbrains.mynasa_md.model.response.SolarFlareResponseData
import com.geekbrains.mynasa_md.viewmodel.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel()
{
    //получить liveData
    fun getLiveData(): LiveData<AppState> = liveData

    //послать серверный запрос
    fun sendRequest(progress: Int? = null){
        liveData.postValue(AppState.Loading(progress))
        repositoryImpl.getEarth(Constants.NASA_API_KEY, callback)
    }

    private val callback = object : Callback<List<EarthResponseData>> {
        override fun onResponse(
            call: Call<List<EarthResponseData>>,
            response: Response<List<EarthResponseData>>
        ) {
            val serverResponse: List<EarthResponseData>? = response.body()
            liveData.postValue(
                if (response.isSuccessful) {
                    checkDataResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(Constants.SERVER_REQUEST_ERROR.toString()))
                }
            )
        }

        private fun checkDataResponse(serverResponse: List<EarthResponseData>?): AppState
        {
            return if (serverResponse == null) {
                AppState.Error(Throwable(Constants.CORRUPTED_DATA.toString()))
            } else {
                AppState.Success(ResponseData(earth = serverResponse))
            }
        }

        override fun onFailure(call: Call<List<EarthResponseData>>, t: Throwable) {
            AppState.Error(Throwable(Constants.PROJECT_ERROR.toString()))
        }
    }
}