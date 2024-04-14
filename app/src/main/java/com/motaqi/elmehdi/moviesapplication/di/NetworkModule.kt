package com.motaqi.elmehdi.moviesapplication.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.motaqi.elmehdi.moviesapplication.constants.NetworkKey
import com.motaqi.elmehdi.moviesapplication.constants.NetworkKey.API_KEY_VALUE
import com.motaqi.elmehdi.moviesapplication.constants.NetworkUrl
import com.motaqi.elmehdi.moviesapplication.constants.ParamsValue
import com.motaqi.elmehdi.moviesapplication.constants.ParamsValue.IS_ADULT_INCLUDED
import com.motaqi.elmehdi.moviesapplication.constants.ParamsValue.PRIMARY_LANGUAGE
import com.motaqi.elmehdi.moviesapplication.constants.Queries
import com.motaqi.elmehdi.moviesapplication.constants.Queries.API_KEY
import com.motaqi.elmehdi.moviesapplication.constants.Queries.INCLUDE_ADULT
import com.motaqi.elmehdi.moviesapplication.constants.Queries.LANGUAGE
import com.motaqi.elmehdi.moviesapplication.constants.Queries.QUERY
import com.motaqi.elmehdi.moviesapplication.data.remote.TheMovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient{

        val queriesInterceptor = Interceptor{ chain ->
            val original = chain.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(API_KEY, API_KEY_VALUE)
                .addQueryParameter(LANGUAGE, PRIMARY_LANGUAGE)
                .addQueryParameter(QUERY,ParamsValue.PRIMARY_TYPE)
                .addQueryParameter(INCLUDE_ADULT, IS_ADULT_INCLUDED)
                .build()

            val requestBuilder = original.newBuilder()
                .url(url)
            val request = requestBuilder.build()
            return@Interceptor chain.proceed(request)
        }

        return OkHttpClient().newBuilder()
            .callTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(queriesInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkUrl.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideTheMovieApiService(retrofit: Retrofit): TheMovieApi {
        return retrofit.create(TheMovieApi::class.java)
    }


}