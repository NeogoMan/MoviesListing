package com.motaqi.elmehdi.moviesapplication.presentation.domain

import android.util.Log
import androidx.paging.PagingData
import com.motaqi.elmehdi.moviesapplication.data.repository.TheMovieListingRepository
import com.motaqi.elmehdi.moviesapplication.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTheMovieListingUseCase @Inject constructor(
    private val theMovieListingRepository: TheMovieListingRepository
) {
    suspend fun execute(): Flow<PagingData<Result>>{
        return theMovieListingRepository.getMovieListing()
    }
}