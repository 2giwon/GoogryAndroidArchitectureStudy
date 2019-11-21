package com.egiwon.architecturestudy.data.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class ContentSearchResult(
    val data: LiveData<PagedList<Content>>,
    val networkError: LiveData<String>
)
