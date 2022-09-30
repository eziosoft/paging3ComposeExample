package com.eziosoft.parisinnumbers.data

import com.eziosoft.parisinnumbers.domain.Paginator

class DefaultPaginator<PageIndex, Page>(
    private val initialPageIndex: PageIndex,
    private inline val onLoadingStatusChangeListener: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: PageIndex) -> Result<Page>,
    private inline val getNextPageIndex: suspend (Page) -> PageIndex,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: Page, newKey: PageIndex) -> Unit
) : Paginator<PageIndex, Page> {
    private var currentPageIndex: PageIndex = initialPageIndex
    private var isMakingRequest = false

    override suspend fun loadNextPage() {
        if (isMakingRequest) return

        isMakingRequest = true
        onLoadingStatusChangeListener(true)

        val result = onRequest(currentPageIndex)
        isMakingRequest = false

        val items = result.getOrElse {
            onError(it)
            onLoadingStatusChangeListener(false)
            return
        }

        currentPageIndex = getNextPageIndex(items)
        onSuccess(items, currentPageIndex)
        onLoadingStatusChangeListener(false)
    }

    override fun reset() {
        currentPageIndex = initialPageIndex
    }
}
