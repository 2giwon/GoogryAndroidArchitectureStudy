package com.egiwon.architecturestudy.data.source.local

import com.egiwon.architecturestudy.data.source.NaverDataSource
import com.egiwon.architecturestudy.data.source.local.model.Content
import com.egiwon.architecturestudy.data.source.remote.response.ContentResponse
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class NaverLocalDataSource(
    private val contentDao: ContentDao
) : NaverDataSource.Local {

    override fun getCacheContents(type: String): Single<ContentResponse> =
        contentDao.getContentCache(type)
            .onErrorReturn { listOf(Content.empty(type, "")) }
            .map {
                ContentResponse(
                    it[0].searchQuery,
                    it.flatMap { content -> content.list }.toList()
                )
            }
            .toSingle()
            .subscribeOn(Schedulers.io())

    override fun getSearchTime(type: String): Single<Long> =
        contentDao.getSearchTime(type)
            .onErrorReturn { 0L }
            .toSingle()
            .subscribeOn(Schedulers.io())


    override fun saveContents(
        type: String,
        query: String,
        response: ContentResponse,
        insertTime: Long
    ): Completable =
        contentDao.insertContent(
            Content(
                response.contentItems,
                type,
                query,
                insertTime
            )
        )


    override fun getContents(
        type: String,
        query: String
    ): Single<ContentResponse> =
        contentDao.getContent(type, query)
            .onErrorReturn { Content.empty(type, "") }
            .map { ContentResponse(it.searchQuery, it.list) }
            .toSingle()
            .subscribeOn(Schedulers.io())

    override fun deleteContents(type: String, query: String): Completable =
        contentDao.deleteContents(type, query)
            .subscribeOn(Schedulers.io())


    companion object {
        private var instance: NaverLocalDataSource? = null

        fun getInstance(contentDao: ContentDao) = instance ?: NaverLocalDataSource(contentDao)
            .apply { instance = this }

    }

}

