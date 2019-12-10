package com.egiwon.architecturestudy.data

import com.egiwon.architecturestudy.data.source.remote.response.ContentResponse
import io.reactivex.Single

interface NaverDataRepository {
    fun loadContents(type: String, query: String): Single<ContentResponse>

    fun getCache(type: String): Single<ContentResponse>

    fun requestMore(type: String, query: String): Single<ContentResponse>

    fun getContents(type: String, query: String): Single<ContentResponse>
}