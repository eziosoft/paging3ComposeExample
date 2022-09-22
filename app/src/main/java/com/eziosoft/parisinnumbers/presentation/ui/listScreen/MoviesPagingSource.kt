package com.eziosoft.parisinnumbers.presentation.ui.listScreen

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eziosoft.parisinnumbers.data.remote.OpenAPI.Datasets
import com.eziosoft.parisinnumbers.data.remote.OpenAPI.MoviesAPI
import com.eziosoft.parisinnumbers.data.remote.OpenAPI.PAGE_SIZE
import java.net.URL
import java.net.URLDecoder

class MoviesPagingSource(
    private val api: MoviesAPI,
    private val dataset: Datasets,
    private val searchText: String
) : PagingSource<Int, com.eziosoft.parisinnumbers.data.remote.OpenAPI.models.records.Record>() {

    override fun getRefreshKey(state: PagingState<Int, com.eziosoft.parisinnumbers.data.remote.OpenAPI.models.records.Record>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.eziosoft.parisinnumbers.data.remote.OpenAPI.models.records.Record> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = api.getMoviesPaged(
                dataset = dataset.title,
                pageStartItem = nextPageNumber,
                pageSize = PAGE_SIZE,
                where = if (searchText.isNotBlank()) "nom_tournage like \"$searchText\"" else null,
                orderBy = "date_debut desc",
                groupBy = null // "nom_tournage"
            )

            val currentPage =
                splitQuery(URL(response.links.find { it.rel == "self" }?.href))["offset"]
                    ?.toInt() ?: 0
//            val firstPage =
//                splitQuery(URL(response.links.find { it.rel == "first" }?.href))["offset"]
//                    ?.toInt() ?: 0
            val lastPage =
                splitQuery(URL(response.links.find { it.rel == "last" }?.href))["offset"]
                    ?.toInt() ?: 0
            val nextPage =
                splitQuery(URL(response.links.find { it.rel == "next" }?.href))["offset"]
                    ?.toInt() ?: 0

            LoadResult.Page(
                data = response.records,
                prevKey = if (currentPage < PAGE_SIZE) null else currentPage - PAGE_SIZE,
                nextKey = if (nextPage > lastPage) null else nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun splitQuery(url: URL): Map<String, String> {
        val queryPairs: MutableMap<String, String> = LinkedHashMap()
        val query = url.query
        val pairs = query.split("&").toTypedArray()
        for (pair in pairs) {
            val idx = pair.indexOf("=")
            queryPairs[URLDecoder.decode(pair.substring(0, idx), "UTF-8")] =
                URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
        }
        return queryPairs
    }
}
