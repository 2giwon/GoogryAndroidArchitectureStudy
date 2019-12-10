package com.egiwon.architecturestudy.data.source.remote

import com.egiwon.architecturestudy.data.source.NaverDataSource
import com.egiwon.architecturestudy.data.source.remote.response.ContentResponse
import com.egiwon.architecturestudy.data.source.service.RetrofitApi
import io.reactivex.Single

class NaverRemoteDataSource :
    NaverDataSource.Remote {

    override fun getContents(
        type: String,
        query: String,
        display: Int,
        start: Int
    ): Single<ContentResponse> =
        RetrofitApi.retrofit.getContentsInfo(
            type = type,
            query = query,
            start = start.toString(),
            display = display.toString()
        )

    companion object {
        private var instance: NaverRemoteDataSource? = null

        fun getInstance() = instance ?: NaverRemoteDataSource()
            .apply { instance = this }
    }
}