package com.egiwon.remote.impl

import com.egiwon.data.NaverRemoteDataSource
import com.egiwon.data.Result
import com.egiwon.data.model.ContentEntity
import com.egiwon.remote.response.ContentResponse
import com.egiwon.remote.response.mapToContentEntity
import com.egiwon.remote.service.ContentService

class NaverRemoteDataSourceImpl(
    private val contentService: ContentService
) : NaverRemoteDataSource {

    override suspend fun getRemoteContents(type: String, query: String): Result<ContentEntity> {
        return try {
            val response: ContentResponse = contentService.getContentInfo(type, query)
            response.query = query
            Result.Success(response.mapToContentEntity())
        } catch (ex: Exception) {
            Result.Failure(ex)
        }
    }

//    override fun getRemoteContents(type: String, query: String): Single<ContentEntity> =
//        contentService.getContentInfo(type, query)
//            .map { response ->
//                response.query = query
//                response.mapToContentEntity()
//            }.subscribeOn(Schedulers.io())


}