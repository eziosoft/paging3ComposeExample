package com.eziosoft.parisinnumbers

import com.eziosoft.MainViewModel
import com.eziosoft.parisinnumbers.data.remote.MoviesAPI
import com.eziosoft.parisinnumbers.data.remote.MoviesRepository
import com.eziosoft.parisinnumbers.data.remote.MoviesRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appmodule = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
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

    viewModel {
        MainViewModel(get())
    }
}
