package com.geekbrains.mynasa_md.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.mynasa_md.model.repo.RepositoryImpl
import com.geekbrains.mynasa_md.model.response.EarthResponseData
import com.geekbrains.mynasa_md.model.response.MarsResponseData
import com.geekbrains.mynasa_md.model.response.PhotoMarsResponseData
import com.geekbrains.mynasa_md.model.response.ResponseData
import com.geekbrains.mynasa_md.viewmodel.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel (
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel()
{
    //получить liveData
    fun getLiveData(): LiveData<AppState> = liveData

    //послать серверный запрос
    fun sendRequest(progress: Int? = null){
        liveData.postValue(AppState.Loading(progress))
        repositoryImpl.getMars(Constants.NASA_API_KEY, callback)
    }

    private val callback = object : Callback<PhotoMarsResponseData> {
        override fun onResponse(
            call: Call<PhotoMarsResponseData>,
            response: Response<PhotoMarsResponseData>
        ) {
            val serverResponse: PhotoMarsResponseData? = response.body()
            liveData.postValue(
                if (response.isSuccessful) {
                    checkDataResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(Constants.SERVER_REQUEST_ERROR.toString()))
                }
            )
        }

        private fun checkDataResponse(serverResponse: PhotoMarsResponseData?): AppState
        {
            return if (serverResponse == null) {
                AppState.Error(Throwable(Constants.CORRUPTED_DATA.toString()))
            } else {
                AppState.Success(ResponseData(mars = serverResponse))
            }
        }

        override fun onFailure(call: Call<PhotoMarsResponseData>, t: Throwable) {
            AppState.Error(Throwable(Constants.PROJECT_ERROR.toString()))
        }
    }
}