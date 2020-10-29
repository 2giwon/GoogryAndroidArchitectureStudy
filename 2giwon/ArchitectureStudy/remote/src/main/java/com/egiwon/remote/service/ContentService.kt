package com.egiwon.remote.service

import com.egiwon.remote.response.ContentResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ContentService {
    @GET("v1/search/{type}.json")
    suspend fun getContentInfo(
        @Path("type")
        type: String,
        @Query("query")
        query: String
    ): ContentResponse
}