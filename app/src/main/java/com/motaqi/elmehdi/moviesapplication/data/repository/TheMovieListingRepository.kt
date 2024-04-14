package com.motaqi.elmehdi.moviesapplication.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.motaqi.elmehdi.moviesapplication.data.remote.TheMovieApi
import com.motaqi.elmehdi.moviesapplication.model.Result
import com.motaqi.elmehdi.moviesapplication.presentation.domain.TheMovieListingPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TheMovieListingRepository @Inject constructor(
    private val theMovieApi: TheMovieApi
) : ITheMovieListingRepository {

    override suspend fun getMovieListing(): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = {
                TheMovieListingPagingSource(
                    theMovieApi = theMovieApi
                )
            }
        ).flow
    }
}