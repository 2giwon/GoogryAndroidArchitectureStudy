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
                    it[0].query,
                    it.flatMap { content -> content.list }.toSet().toList()
                )
            }
            .toSingle()
            .subscribeOn(Schedulers.io())

    override fun saveContents(type: String, query: String, response: ContentResponse): Completable =
        contentDao.insertContent(
            Content(
                System.currentTimeMillis(),
                response.contentItems,
                type,
                query
            )
        )

    override fun getContents(
        type: String,
        query: String
    ): Single<ContentResponse> =
        contentDao.getContent(type, query)
            .onErrorReturn { Content.empty(type, "") }
            .map { ContentResponse(it.query, it.list) }
            .toSingle()
            .subscribeOn(Schedulers.io())

    override fun deleteContents(type: String): Completable =
        contentDao.deleteContentsByType(type)
            .subscribeOn(Schedulers.io())


    companion object {
        private var instance: NaverLocalDataSource? = null

        fun getInstance(contentDao: ContentDao) = instance ?: NaverLocalDataSource(contentDao)
            .apply { instance = this }

    }

}

