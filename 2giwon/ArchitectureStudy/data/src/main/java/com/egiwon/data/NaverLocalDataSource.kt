package com.egiwon.data

import com.egiwon.data.model.ContentEntity

interface NaverLocalDataSource {
    suspend fun getCacheContents(type: String): Result<ContentEntity>

    suspend fun getContentQueries(type: String): Result<List<String>>

    suspend fun getLocalContents(type: String, query: String): Result<ContentEntity>

    suspend fun saveContents(type: String, query: String, response: ContentEntity)
}