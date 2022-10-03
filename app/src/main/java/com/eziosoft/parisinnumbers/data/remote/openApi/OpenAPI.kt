package com.eziosoft.parisinnumbers.data.remote.openApi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val PAGE_SIZE = 100

interface MoviesAPI {
    companion object {
        const val BASE_URL = "https://opendata.paris.fr/api/v2/catalog/datasets/"
    }

    @GET("{dataset}/exports/json")
    suspend fun getAllMovies(
        @Path("dataset") datasets: String
    ): Response<com.eziosoft.parisinnumbers.data.remote.openApi.models.allMovies.AllMovies>
}
