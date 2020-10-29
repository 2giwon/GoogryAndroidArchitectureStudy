package com.egiwon.data

import com.egiwon.data.model.ContentEntity

interface NaverDataRepository {
    suspend fun getContents(type: String, query: String): Result<ContentEntity>

    suspend fun getContentsByHistory(type: String, query: String): Result<ContentEntity>

    suspend fun getContentQueries(type: String): Result<List<String>>

    suspend fun getCache(type: String): Result<ContentEntity>
}