package com.eziosoft.parisinnumbers.data.remote.theMovieDbApi.models

data class SearchResult(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)
