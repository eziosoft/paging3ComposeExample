package com.eziosoft.parisinnumbers.data.remote.openApi

import com.eziosoft.parisinnumbers.data.remote.openApi.models.allMovies.AllMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesAPI {
    companion object {
        const val BASE_URL = "https://opendata.paris.fr/api/v2/catalog/datasets/"
    }

    @GET("{dataset}/exports/json")
    suspend fun getAllMovies(
        @Path("dataset") datasets: String
    ): Response<AllMovies>
}

const val PAGE_SIZE = 100

enum class OpenApiDataset(val title: String) {
    MOVIES("lieux-de-tournage-a-paris")
}
