package com.eziosoft.parisinnumbers.domain

interface Paginator<Key, Page> {
    suspend fun loadNextPage()
    fun reset()
}
