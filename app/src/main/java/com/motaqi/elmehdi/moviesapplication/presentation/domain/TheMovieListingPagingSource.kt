package com.motaqi.elmehdi.moviesapplication.presentation.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.motaqi.elmehdi.moviesapplication.data.remote.TheMovieApi
import com.motaqi.elmehdi.moviesapplication.model.Result
import javax.inject.Inject

class TheMovieListingPagingSource @Inject constructor(
    private val theMovieApi: TheMovieApi
): PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val page = params.key ?: 1
            val response = theMovieApi.getMovieListing(page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else response.page.plus(1)
            )

        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}