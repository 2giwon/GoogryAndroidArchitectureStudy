package com.egiwon.architecturestudy.data.source.remote

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.egiwon.architecturestudy.BuildConfig
import com.egiwon.architecturestudy.data.Content
import com.egiwon.architecturestudy.data.source.service.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NaverRemoteDataSource : NaverDataSource, PageKeyedDataSource<Int, Content.Item>() {
    lateinit var type: String
    lateinit var query: String

    private var isRequestInProgress = false

    private var lastRequestPage = 1
    override fun getContents(
        query: String,
        page: String,
        display: String,
        onSuccess: (content: Content) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {

    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Content.Item>
    ) {

        if (isRequestInProgress) return

        isRequestInProgress = true

        RetrofitApi.retrofit.getContentsInfo(
            type = type,
            query = query,
            start = lastRequestPage.toString(),
            display = params.requestedLoadSize.toString()
        ).enqueue(object : Callback<Content> {

            override fun onFailure(call: Call<Content>, t: Throwable) {
                t.let {
                    if (BuildConfig.DEBUG) {
                        Log.d("RetroFit", "onFailure ${it.message}")
                    }

                    loadInitial(params, callback)
                }
            }

            override fun onResponse(
                call: Call<Content>,
                response: Response<Content>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        lastRequestPage += params.requestedLoadSize
                        callback.onResult(response.body()!!.items, null, 2)
                    } else {
                        loadInitial(params, callback)
                    }
                } else {
                    loadInitial(params, callback)
                }
            }
        })


    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Content.Item>
    ) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        RetrofitApi.retrofit.getContentsInfo(
            type = type,
            query = query,
            start = lastRequestPage.toString(),
            display = params.requestedLoadSize.toString()
        ).enqueue(object : Callback<Content> {

            override fun onFailure(call: Call<Content>, t: Throwable) {
                t.let {
                    if (BuildConfig.DEBUG) {
                        Log.d("RetroFit", "onFailure ${it.message}")
                    }

                    loadAfter(params, callback)
                }
            }

            override fun onResponse(
                call: Call<Content>,
                response: Response<Content>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        lastRequestPage += params.requestedLoadSize
                        callback.onResult(response.body()!!.items, params.key + 1)
                    } else {
                        loadAfter(params, callback)
                    }
                } else {
                    loadAfter(params, callback)
                }
            }
        })
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Content.Item>
    ) = Unit


    companion object {
        private var instance: NaverRemoteDataSource? = null

        fun getInstance() = instance ?: NaverRemoteDataSource()
            .apply { instance = this }
    }
}