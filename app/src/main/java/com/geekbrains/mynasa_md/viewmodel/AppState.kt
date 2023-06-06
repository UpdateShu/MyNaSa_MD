package com.geekbrains.mynasa_md.viewmodel

import com.geekbrains.mynasa_md.model.response.ResponseData

sealed class AppState {
    data class Success(val responseData: ResponseData) : AppState()

    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}