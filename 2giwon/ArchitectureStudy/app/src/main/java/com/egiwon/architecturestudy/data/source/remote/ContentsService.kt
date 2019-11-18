package com.egiwon.architecturestudy.data.source.remote

import com.egiwon.architecturestudy.data.Content
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ContentsService {
    @GET("search/{type}.json")
    fun getContentsInfo(
        @Path("type")
        type: String,
        @Query("query")
        query: String,
        @Query("start")
        start: String,
        @Query("display")
        display: String
    ): Call<Content>
}