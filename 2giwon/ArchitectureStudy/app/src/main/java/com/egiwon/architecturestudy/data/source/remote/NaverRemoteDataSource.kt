package com.egiwon.architecturestudy.data.source.remote

import android.util.Log
import com.egiwon.architecturestudy.BuildConfig
import com.egiwon.architecturestudy.data.model.Content
import com.egiwon.architecturestudy.data.model.ContentSearchResponse
import com.egiwon.architecturestudy.data.source.service.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NaverRemoteDataSource : NaverDataSource {

    override fun getContents(
        type: String,
        query: String,
        page: String,
        display: String,
        onSuccess: (content: List<Content>) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        RetrofitApi.retrofit.getContentsInfo(
            type = type,
            query = query,
            start = page,
            display = display
        ).enqueue(object : Callback<ContentSearchResponse> {

            override fun onFailure(call: Call<ContentSearchResponse>, t: Throwable) {
                t.let {
                    if (BuildConfig.DEBUG) {
                        Log.d("RetroFit", "onFailure ${it.message}")
                    }
                    onFailure(it)
                }
            }

            override fun onResponse(
                call: Call<ContentSearchResponse>,
                response: Response<ContentSearchResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {

                        onSuccess(response.body()!!.items)
                    } else {
                        onFailure(Throwable())
                    }
                } else {
                    onFailure(Throwable())
                }
            }
        })

    }


    companion object {
        private var instance: NaverRemoteDataSource? = null

        fun getInstance() = instance ?: NaverRemoteDataSource()
            .apply { instance = this }
    }
}