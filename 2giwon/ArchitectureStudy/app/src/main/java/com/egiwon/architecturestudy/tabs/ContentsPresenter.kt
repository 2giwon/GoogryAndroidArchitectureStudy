package com.egiwon.architecturestudy.tabs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.egiwon.architecturestudy.data.model.Content
import com.egiwon.architecturestudy.data.model.ContentSearchResult
import com.egiwon.architecturestudy.data.source.NaverRepository

class ContentsPresenter(
    private val naverDataRepository: NaverRepository
) : ContentsContract.Presenter {
    private lateinit var type: String

    private val queryLiveData = MutableLiveData<String>()
    private val contentsSearchResult: LiveData<ContentSearchResult> =
        Transformations.map(queryLiveData) {
            naverDataRepository.getContents(type, it)
        }


    override val contents: LiveData<PagedList<Content>> =
        Transformations.switchMap(contentsSearchResult) {
            it.data
        }

    override fun loadContents(
        type: String,
        query: String
    ) {
        this.type = type
        queryLiveData.postValue(query)
    }

    override fun start() = Unit

}