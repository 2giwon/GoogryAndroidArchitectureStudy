package com.egiwon.architecturestudy.data

import androidx.lifecycle.MutableLiveData
import com.egiwon.architecturestudy.data.source.NaverDataSource
import com.egiwon.architecturestudy.data.source.remote.response.ContentResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class NaverDataRepositoryImpl(
    private val naverRemoteDataSource: NaverDataSource.Remote,
    private val naverLocalDataSource: NaverDataSource.Local
) : NaverDataRepository {

    private var lastQueryPage = START_PAGE

    private val lastSearchTime = MutableLiveData<Long>()

    private val lastQuery = MutableLiveData<String>()

    private fun loadRemoteContents(type: String, query: String): Single<ContentResponse> =
        naverRemoteDataSource.getContents(
            type = type,
            query = query,
            display = DISPLAY_PAGE,
            start = lastQueryPage
        ).flatMap { response ->
            if (response.contentItems.isNotEmpty()) {
                val immutableLastInsertTime = getLastInsertTime()
                if (immutableLastInsertTime != null) {
                    naverLocalDataSource.saveContents(
                        type,
                        query,
                        response,
                        immutableLastInsertTime
                    ).andThen(getContents(type, query))
                } else {
                    Single.create { Throwable() }
                }
            } else {
                Single.create { Throwable() }
            }
        }.subscribeOn(Schedulers.io())
            .doOnSubscribe { lastQueryPage += DISPLAY_PAGE }

    override fun loadContents(type: String, query: String): Single<ContentResponse> {
        lastQueryPage = START_PAGE
        lastSearchTime.postValue(System.currentTimeMillis())
        return if (getLastQuery() != null) {
            naverLocalDataSource.deleteContents(type, getLastQuery()!!)
                .andThen(loadRemoteContents(type, query))
        } else {
            loadRemoteContents(type, query)
        }.doAfterSuccess { lastQuery.postValue(query) }
    }

    override fun getCache(type: String): Single<ContentResponse> =
        naverLocalDataSource.getSearchTime(type)
            .flatMap { insertTime ->
                if (insertTime != 0L) {
                    lastSearchTime.postValue(insertTime)

                    naverLocalDataSource.getCacheContents(type)
                        .doAfterSuccess { lastQueryPage += it.contentItems.size }
                } else {
                    Single.create<ContentResponse> { ContentResponse("", emptyList()) }
                }
            }


    override fun requestMore(type: String, query: String): Single<ContentResponse> =
        loadRemoteContents(type, query)

    override fun getContents(type: String, query: String): Single<ContentResponse> =
        naverLocalDataSource.getContents(type, query)

    private fun getLastInsertTime(): Long? = lastSearchTime.value

    private fun getLastQuery(): String? = lastQuery.value

    companion object {
        private const val DISPLAY_PAGE = 10
        private const val START_PAGE = 1
        private var instance: NaverDataRepositoryImpl? = null

        fun getInstance(
            naverDataSource: NaverDataSource.Remote,
            naverLocalDataSource: NaverDataSource.Local
        ): NaverDataRepositoryImpl =
            instance
                ?: NaverDataRepositoryImpl(
                    naverDataSource,
                    naverLocalDataSource
                ).apply {
                    instance = this
                }
    }
}