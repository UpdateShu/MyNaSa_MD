package com.geekbrains.mynasa_md.utils

import com.geekbrains.mynasa_md.BuildConfig
import com.geekbrains.mynasa_md.R

object Constants {

    const val NASA_API_KEY = BuildConfig.NASA_API_KEY
    const val TAG_BS = "BottomSheetDialog"
    const val TAG_MA = "MainActivity"
    const val WIKI_URI = "https://www.en.wikipedia.org/wiki/"

    const val SERVER_REQUEST_ERROR = R.string.server_request_error
    const val PROJECT_ERROR = R.string.project_error
    const val CORRUPTED_DATA = R.string.corrupted_data

    const val BACKSTACK = true
    const val NO_BACKSTACK = false

    const val TODAY = 0
    const val YESTERDAY = 1
    const val DAY_BEFORE_YESTERDAY = 2

    const val TYPE_HEADER: Int = 1
    const val TYPE_NOTE: Int = 2
}