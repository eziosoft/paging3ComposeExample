package com.eziosoft.parisinnumbers.data.remote.openApi

import com.eziosoft.parisinnumbers.data.remote.openApi.models.records.MoviesPage
import com.eziosoft.parisinnumbers.data.remote.openApi.models.titles.MovieTitles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val PAGE_SIZE = 10

interface MoviesAPI {
    companion object {
        const val BASE_URL = "https://opendata.paris.fr/api/v2/catalog/datasets/"
    }

    @GET("{dataset}/records")
    suspend fun getPage(
        @Path("dataset") dataset: String,
        @Query("offset") pageStartItem: Int,
        @Query("limit") pageSize: Int,
        @Query("where") where: String?, // ex "nom_tournage like \"ALICE\""
        @Query("order_by") orderBy: String?,
        @Query("group_by") groupBy: String?
    ): Response<MoviesPage>

    @GET("{dataset}/records")
    suspend fun getMovieTitlesPage(
        @Path("dataset") dataset: String,
        @Query("offset") pageStartItem: Int,
        @Query("limit") pageSize: Int,
        @Query("where") where: String?, // ex "nom_tournage like \"ALICE\""
        @Query("order_by") orderBy: String?,
        @Query("group_by") groupBy: String?
    ): Response<MovieTitles>


    @GET("{dataset}/records/{record_id}")
    suspend fun getMovieById(
        @Path("dataset") datasets: String,
        @Path("record_id") recordId: String
    ): Response<com.eziosoft.parisinnumbers.data.remote.openApi.models.singleRecord.SingleRecord>

    @GET("{dataset}/exports/json")
    suspend fun getAllMovies(
        @Path("dataset") datasets: String
    ): Response<com.eziosoft.parisinnumbers.data.remote.openApi.models.allMovies.AllMovies>
}
