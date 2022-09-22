package com.eziosoft.parisinnumbers.data.remote.theMovieDb

import com.eziosoft.parisinnumbers.data.remote.theMovieDb.models.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDb {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @GET("search/movie")
    suspend fun search(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Response<SearchResult>
}
