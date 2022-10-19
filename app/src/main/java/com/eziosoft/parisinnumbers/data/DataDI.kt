package com.eziosoft.parisinnumbers.data

import com.eziosoft.parisinnumbers.R
import com.eziosoft.parisinnumbers.data.remote.OpenApiRepositoryImpl
import com.eziosoft.parisinnumbers.data.remote.TheMovieDbApiRepositoryImpl
import com.eziosoft.parisinnumbers.data.remote.openApi.MoviesAPI
import com.eziosoft.parisinnumbers.data.remote.theMovieDbApi.TheMovieDbApi
import com.eziosoft.parisinnumbers.domain.repository.OpenApiRepository
import com.eziosoft.parisinnumbers.domain.repository.TheMovieDbApiRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

val apiModule = module {
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
        OpenApiRepositoryImpl(get())
    }

    single<TheMovieDbApi> {
        val retrofit = Retrofit.Builder()
            .baseUrl(TheMovieDbApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(TheMovieDbApi::class.java)
    }

    single<TheMovieDbApiRepository> {
        TheMovieDbApiRepositoryImpl(
            api = get(),
            androidContext().resources.getString(R.string.THE_MOVIES_DB_KEY),
            get()
        )
    }
}
