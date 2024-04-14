package com.motaqi.elmehdi.moviesapplication.extension

import com.motaqi.elmehdi.moviesapplication.constants.NetworkUrl

fun String.toImageUrl(): String{
    return NetworkUrl.BASE_URL_POSTER.plus(this)
}