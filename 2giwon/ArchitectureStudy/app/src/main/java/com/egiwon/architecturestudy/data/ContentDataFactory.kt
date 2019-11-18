package com.egiwon.architecturestudy.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.egiwon.architecturestudy.data.source.remote.NaverRemoteDataSource

class ContentDataFactory(
    private val type: String,
    private val query: String
) : DataSource.Factory<Int, Content.Item>() {

    private val mutableNaverRemoteSource = MutableLiveData<NaverRemoteDataSource>()
    private lateinit var naverRemoteDataSource: NaverRemoteDataSource

    override fun create(): DataSource<Int, Content.Item> {
        naverRemoteDataSource = NaverRemoteDataSource.getInstance()
        naverRemoteDataSource.query = query
        naverRemoteDataSource.type = type
        mutableNaverRemoteSource.postValue(naverRemoteDataSource)
        return naverRemoteDataSource
    }

    fun getMutableLiveData(): MutableLiveData<NaverRemoteDataSource> =
        mutableNaverRemoteSource
}