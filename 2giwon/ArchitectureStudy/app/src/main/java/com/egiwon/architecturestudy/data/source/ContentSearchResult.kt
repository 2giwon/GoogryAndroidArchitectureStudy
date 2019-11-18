package com.egiwon.architecturestudy.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.egiwon.architecturestudy.data.Content

data class ContentSearchResult(
    val data: LiveData<PagedList<Content.Item>>,
    val networkError: Throwable
)
