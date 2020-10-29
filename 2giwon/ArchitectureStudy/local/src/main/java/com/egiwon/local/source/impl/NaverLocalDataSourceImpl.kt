package com.egiwon.local.source.impl

import com.egiwon.data.NaverLocalDataSource
import com.egiwon.data.Result
import com.egiwon.data.model.ContentEntity
import com.egiwon.local.ContentDao
import com.egiwon.local.model.Content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NaverLocalDataSourceImpl(
    private val contentDao: ContentDao
) : NaverLocalDataSource {

    override suspend fun getCacheContents(type: String): Result<ContentEntity> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val content: Content = contentDao.getContentCache(type)
                Result.Success(ContentEntity(query = content.query, contentItems = content.list))
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }

    override suspend fun getContentQueries(type: String): Result<List<String>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.Success(contentDao.getContentQuery(type))
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }

    override suspend fun getLocalContents(type: String, query: String): Result<ContentEntity> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val content: Content = contentDao.getContents(type = type, query = query)
                Result.Success(ContentEntity(query = content.query, contentItems = content.list))
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }

    override suspend fun saveContents(type: String, query: String, response: ContentEntity) =
        contentDao.insertContent(
            Content(
                System.currentTimeMillis(),
                response.contentItems,
                type,
                query
            )
        )

//    override fun getCacheContents(type: String): Single<ContentEntity> =
//        contentDao.getContentCache(type)
//            .onErrorReturn { Content.empty(type, "") }
//            .map { ContentEntity(it.query, it.list) }
//            .toSingle()
//            .subscribeOn(Schedulers.io())
//
//    override fun getContentQueries(type: String): Single<List<String>> =
//        contentDao.getContentQuery(type)
//            .onErrorReturn { emptyList() }
//            .toSingle()
//            .subscribeOn(Schedulers.io())
//
//    override fun getLocalContents(type: String, query: String): Single<ContentEntity> =
//        contentDao.getContents(type, query)
//            .onErrorReturn { Content.empty(type, query) }
//            .map { ContentEntity(it.query, it.list) }
//            .toSingle()
//            .subscribeOn(Schedulers.io())
//
//    override fun saveContents(type: String, query: String, response: ContentEntity) =
//        contentDao.insertContent(
//            Content(
//                System.currentTimeMillis(),
//                response.contentItems,
//                type,
//                query
//            )
//        )
}