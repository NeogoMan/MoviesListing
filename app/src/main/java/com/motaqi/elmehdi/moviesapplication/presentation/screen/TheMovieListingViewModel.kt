package com.motaqi.elmehdi.moviesapplication.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.motaqi.elmehdi.moviesapplication.model.Result
import com.motaqi.elmehdi.moviesapplication.presentation.domain.GetTheMovieListingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TheMovieListingViewModel @Inject constructor(
    private val getTheMovieListingUseCase: GetTheMovieListingUseCase
): ViewModel() {

    private val _theMovieListingState: MutableStateFlow<PagingData<Result>> = MutableStateFlow(value = PagingData.empty())
    val theMovieListingState: StateFlow<PagingData<Result>> get() = _theMovieListingState

    init {
        getMovies()
    }

    fun searchEvent(value: String = ""){
        if (value.isEmpty()) getMovies()
        _theMovieListingState.value =  _theMovieListingState.value.filter {
            it.name.startsWith(value, ignoreCase = true)
        }
    }

    private fun getMovies(){
        viewModelScope.launch {
            getTheMovieListingUseCase
                .execute()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                    _theMovieListingState.value = it.filter {
                        !it.poster_path.isNullOrEmpty()
                    }
                }
        }
    }
}