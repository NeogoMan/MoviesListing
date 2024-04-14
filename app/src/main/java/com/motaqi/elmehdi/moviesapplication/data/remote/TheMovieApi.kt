package com.motaqi.elmehdi.moviesapplication.data.remote

import com.motaqi.elmehdi.moviesapplication.constants.GetApi
import com.motaqi.elmehdi.moviesapplication.model.TheMovieListingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieApi {

    @GET(GetApi.GET_SEARCH_MOVIE)
    suspend fun getMovieListing(@Query("page") page: Int): TheMovieListingResponse
}