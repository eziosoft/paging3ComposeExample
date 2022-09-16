package com.eziosoft.parisinnumbers.data

import com.eziosoft.parisinnumbers.data.remote.MoviesAPI
import com.eziosoft.parisinnumbers.domain.MoviesRepository
import com.eziosoft.parisinnumbers.data.remote.MoviesRepositoryImpl
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

val dataModule = module {
    single<MoviesAPI> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.NONE

        val okHttpClient = OkHttpClient.Builder().cache(
            Cache(
                File(androidContext().cacheDir, "http_cache"),
                50L * 1024L * 1024L // 50 MiB
            )
        ).addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(MoviesAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(MoviesAPI::class.java)
    }

    single<MoviesRepository> {
        MoviesRepositoryImpl(get())
    }
}
