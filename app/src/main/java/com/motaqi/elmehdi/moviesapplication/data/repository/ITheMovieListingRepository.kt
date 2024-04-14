package com.motaqi.elmehdi.moviesapplication.data.repository

import androidx.paging.PagingData
import com.motaqi.elmehdi.moviesapplication.model.Result
import kotlinx.coroutines.flow.Flow

interface ITheMovieListingRepository {
    suspend fun getMovieListing(): Flow<PagingData<Result>>
}