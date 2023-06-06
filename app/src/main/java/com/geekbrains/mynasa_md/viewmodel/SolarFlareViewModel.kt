package com.geekbrains.mynasa_md.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.mynasa_md.model.repo.RepositoryImpl
import com.geekbrains.mynasa_md.model.response.ResponseData
import com.geekbrains.mynasa_md.model.response.SolarFlareResponseData
import com.geekbrains.mynasa_md.viewmodel.utils.Constants
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.CORRUPTED_DATA
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.PROJECT_ERROR
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.SERVER_REQUEST_ERROR
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SolarFlareViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel()
{
    //получить liveData
    fun getLiveData(): LiveData<AppState> = liveData

    //послать серверный запрос
    fun sendRequest(progress: Int? = null){
        liveData.postValue(AppState.Loading(progress))
        repositoryImpl.getSolarFlares(Constants.NASA_API_KEY, callback)
    }

    private val callback = object : Callback<List<SolarFlareResponseData>> {
        override fun onResponse(
            call: Call<List<SolarFlareResponseData>>,
            response: Response<List<SolarFlareResponseData>>
        ) {
            val serverResponse: List<SolarFlareResponseData>? = response.body()
            liveData.postValue(
                if (response.isSuccessful) {
                    checkDataResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_REQUEST_ERROR.toString()))
                }
            )
        }

        private fun checkDataResponse(serverResponse: List<SolarFlareResponseData>?): AppState
        {
            return if (serverResponse == null) {
                AppState.Error(Throwable(CORRUPTED_DATA.toString()))
            } else {
                AppState.Success(ResponseData(solarFlares = serverResponse))
            }
        }

        override fun onFailure(call: Call<List<SolarFlareResponseData>>, t: Throwable) {
            AppState.Error(Throwable(PROJECT_ERROR.toString()))
        }
    }
}