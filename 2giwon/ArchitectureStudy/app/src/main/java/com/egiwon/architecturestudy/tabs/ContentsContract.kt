package com.egiwon.architecturestudy.tabs

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.egiwon.architecturestudy.base.BasePresenter
import com.egiwon.architecturestudy.base.BaseView
import com.egiwon.architecturestudy.data.model.Content

interface ContentsContract {
    interface View : BaseView<Presenter> {
        fun onUpdateUi(contents: PagedList<Content>)
        fun onFail(throwable: Throwable)
    }

    interface Presenter : BasePresenter {
        fun loadContents(type: String, query: String)
        val contents: LiveData<PagedList<Content>>
    }
}