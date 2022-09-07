package com.eziosoft.parisinnumbers.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eziosoft.parisinnumbers.data.remote.models.Record
import java.net.URL
import java.net.URLDecoder

class MoviesPagingSource(
    private val api: MoviesAPI,
    private val dataset: Datasets
) : PagingSource<Int, Record>() {

    override fun getRefreshKey(state: PagingState<Int, Record>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Record> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = api.getMovies(
                dataset = dataset.title,
                pageStartItem = nextPageNumber,
                pageSize = PAGE_SIZE,
                where = null, // "nom_tournage like \"ALICE\"",
                orderBy = null,
                groupBy = null
            )

            val currentPage =
                splitQuery(URL(response.links.find { it.rel == "self" }?.href))?.get("offset")
                    ?.toInt() ?: 0
            val firstPage =
                splitQuery(URL(response.links.find { it.rel == "first" }?.href))?.get("offset")
                    ?.toInt() ?: 0
            val lastPage =
                splitQuery(URL(response.links.find { it.rel == "last" }?.href))?.get("offset")
                    ?.toInt() ?: 0
            val nextPage =
                splitQuery(URL(response.links.find { it.rel == "next" }?.href))?.get("offset")
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

    private fun splitQuery(url: URL): Map<String, String>? {
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
