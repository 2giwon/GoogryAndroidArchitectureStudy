package com.egiwon.architecturestudy.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.egiwon.architecturestudy.data.model.Content
import com.egiwon.architecturestudy.data.source.NaverRepositoryImpl.Companion.LOAD_PAGE_SIZE
import com.egiwon.architecturestudy.data.source.local.ContentDataCache
import com.egiwon.architecturestudy.data.source.remote.NaverRemoteDataSource

class ContentBoundaryCallback(
    private val type: String,
    private val query: String,
    private val cache: ContentDataCache,
    private val remoteDataSource: NaverRemoteDataSource
) : PagedList.BoundaryCallback<Content>() {

    private var isRequestInProgress = false

    private var lastRequestPage = 1
    private var id = 1L

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    override fun onZeroItemsLoaded() {
        requestSearch(type, query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Content) {
        requestSearch(type, query)
    }

    private fun requestSearch(type: String, query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        remoteDataSource.getContents(
            type,
            query,
            lastRequestPage.toString(),
            LOAD_PAGE_SIZE.toString(),
            onSuccess = { contents ->
                contents.forEach {
                    it.query = query
                    it.id = id++
                }
                cache.insert(contents) {
                    lastRequestPage += LOAD_PAGE_SIZE
                    isRequestInProgress = false
                }
            },
            onFailure = { error ->
                _networkErrors.postValue(error.message)
                isRequestInProgress = false
            }
        )
    }
}