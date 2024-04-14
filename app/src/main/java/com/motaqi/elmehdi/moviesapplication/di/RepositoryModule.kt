package com.motaqi.elmehdi.moviesapplication.di

import com.motaqi.elmehdi.moviesapplication.presentation.domain.GetTheMovieListingUseCase
import com.motaqi.elmehdi.moviesapplication.data.remote.TheMovieApi
import com.motaqi.elmehdi.moviesapplication.data.repository.TheMovieListingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTheMovieListingRepository(
        theMovieApi: TheMovieApi
    ): TheMovieListingRepository {
        return TheMovieListingRepository(theMovieApi)
    }

    @Provides
    @Singleton
    fun provideTheMovieListingUseCase(
        theMovieListingRepository: TheMovieListingRepository
    ): GetTheMovieListingUseCase {
        return GetTheMovieListingUseCase(theMovieListingRepository)
    }
}