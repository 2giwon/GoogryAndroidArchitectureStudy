package com.egiwon.architecturestudy.data

import com.egiwon.architecturestudy.data.source.NaverDataSource
import com.egiwon.architecturestudy.data.source.remote.response.ContentResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class NaverDataRepositoryImpl(
    private val naverRemoteDataSource: NaverDataSource.Remote,
    private val naverLocalDataSource: NaverDataSource.Local
) : NaverDataRepository {

    private var lastQueryPage = START_PAGE
    private fun loadRemoteContents(type: String, query: String): Single<ContentResponse> =
        naverRemoteDataSource.getContents(
            type = type,
            query = query,
            display = DISPLAY_PAGE,
            start = lastQueryPage
        ).flatMap { response ->
            if (response.contentItems.isNotEmpty()) {
                lastQueryPage += DISPLAY_PAGE
                naverLocalDataSource.saveContents(
                    type,
                    query,
                    response
                ).toSingle { response }

            } else {
                Single.create { Throwable() }
            }
        }


    override fun loadContents(type: String, query: String): Single<ContentResponse> {
        lastQueryPage = START_PAGE
        return loadRemoteContents(type, query)
    }

    override fun getCache(type: String): Single<ContentResponse> =
        naverLocalDataSource.getCacheContents(type)
            .doAfterSuccess { lastQueryPage += it.contentItems.size }


    override fun requestMore(type: String, query: String): Single<ContentResponse> =
        loadRemoteContents(type, query)
            .subscribeOn(Schedulers.io())
            .flatMap {
                if (it.contentItems.isNotEmpty()) {
                    getContents(
                        type,
                        query
                    )
                } else {
                    Single.create { Throwable() }
                }
            }

    override fun getContents(type: String, query: String): Single<ContentResponse> =
        naverLocalDataSource.getContents(type, query)


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