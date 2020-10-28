package com.egiwon.data

import com.egiwon.data.model.ContentEntity

interface NaverRemoteDataSource {
    suspend fun getRemoteContents(type: String, query: String): Result<ContentEntity>
}