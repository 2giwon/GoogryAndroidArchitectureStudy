package com.egiwon.data.impl

import com.egiwon.data.NaverDataRepository
import com.egiwon.data.NaverLocalDataSource
import com.egiwon.data.NaverRemoteDataSource
import com.egiwon.data.Result
import com.egiwon.data.model.ContentEntity

class NaverDataRepositoryImpl(
    private val naverRemoteDataSource: NaverRemoteDataSource,
    private val naverLocalDataSource: NaverLocalDataSource
) : NaverDataRepository {

//    private fun loadRemoteContents(type: String, query: String): Result<ContentEntity> =
//        naverRemoteDataSource.getRemoteContents(type, query)
//            .flatMap { response ->
//                naverLocalDataSource.saveContents(type, query, response)
//                    .toSingle { response }
//            }

    private suspend fun loadRemoteContents(type: String, query: String): Result<ContentEntity> =
        when (val result = naverRemoteDataSource.getRemoteContents(type, query)) {
            is Result.Success -> {
                naverLocalDataSource.saveContents(type, query, result.data)
                result
            }
            is Result.Failure -> result
        }

    override suspend fun getContents(
        type: String,
        query: String
    ): Result<ContentEntity> = loadRemoteContents(type, query)

    override suspend fun getContentsByHistory(
        type: String,
        query: String
    ): Result<ContentEntity> = naverLocalDataSource.getLocalContents(type, query)

    override suspend fun getContentQueries(type: String): Result<List<String>> =
        naverLocalDataSource.getContentQueries(type)

    override suspend fun getCache(type: String): Result<ContentEntity> =
        naverLocalDataSource.getCacheContents(type)

//    override suspend fun getContents(type: String, query: String): com.egiwon.data.Result<ContentEntity> =
//        loadRemoteContents(type, query)
//
//    override suspend fun getContentsByHistory(type: String, query: String): Result<ContentEntity> =
//        naverLocalDataSource.getLocalContents(type, query)
//
//    override suspend fun getContentQueries(type: String): Result<List<String>> =
//        naverLocalDataSource.getContentQueries(type)
//
//    override suspend fun getCache(type: String): Result<ContentEntity> =
//        naverLocalDataSource.getCacheContents(type)

}