package com.eziosoft.parisinnumbers.data.remote

import com.eziosoft.parisinnumbers.data.remote.models.Movies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val PAGE_SIZE = 100

enum class Datasets(val title: String) {
    MOVIES("lieux-de-tournage-a-paris"),
}

interface MoviesAPI {
    companion object {
        const val BASE_URL = "https://opendata.paris.fr/api/v2/catalog/datasets/"
    }

    // https://opendata.paris.fr/api/v2/catalog/datasets/lieux-de-tournage-a-paris/records?limit=10&offset=0
    @GET("{dataset}/records")
    suspend fun getMovies(
        @Path("dataset") dataset: String,
        @Query("offset") pageStartItem: Int,
        @Query("limit") pageSize: Int,
        @Query("where") where: String?, // ex "nom_tournage like \"ALICE\""
        @Query("order_by") orderBy: String?,
        @Query("group_by") groupBy: String?
    ): Movies
}
