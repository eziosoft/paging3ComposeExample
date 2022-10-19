package com.eziosoft.parisinnumbers.domain.paginator

interface Paginator<Key, Page> {
    suspend fun loadNextPage()
    fun reset()
}
