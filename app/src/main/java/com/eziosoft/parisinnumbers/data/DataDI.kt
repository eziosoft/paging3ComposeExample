package com.eziosoft.parisinnumbers.data

import com.eziosoft.parisinnumbers.data.remote.OpenAPI.MoviesAPI
import com.eziosoft.parisinnumbers.data.remote.OpenApiImpl
import com.eziosoft.parisinnumbers.data.remote.TheMovieDbRepositoryImpl
import com.eziosoft.parisinnumbers.data.remote.theMovieDb.TheMovieDb
import com.eziosoft.parisinnumbers.domain.OpenApiRepository
import com.eziosoft.parisinnumbers.domain.TheMovieDbRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

val dataModule = module {

    single<OkHttpClient> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.NONE

        OkHttpClient.Builder().cache(
            Cache(
                File(androidContext().cacheDir, "http_cache"),
                50L * 1024L * 1024L // 50 MiB
            )
        ).addInterceptor(interceptor).build()
    }

    single<MoviesAPI> {
        val retrofit = Retrofit.Builder()
            .baseUrl(MoviesAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(MoviesAPI::class.java)
    }

    single<OpenApiRepository> {
        OpenApiImpl(get())
    }

    single<TheMovieDb> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder().cache(
            Cache(
                File(androidContext().cacheDir, "http_cache"),
                50L * 1024L * 1024L // 50 MiB
            )
        ).addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(TheMovieDb.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(TheMovieDb::class.java)
    }

    single<TheMovieDbRepository> {
        TheMovieDbRepositoryImpl(api = get())
    }
}
