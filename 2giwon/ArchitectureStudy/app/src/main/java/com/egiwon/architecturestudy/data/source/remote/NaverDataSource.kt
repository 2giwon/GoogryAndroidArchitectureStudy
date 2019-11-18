package com.egiwon.architecturestudy.data.source.remote

import com.egiwon.architecturestudy.data.Content

interface NaverDataSource {

    fun getContents(
        query: String,
        page: String,
        display: String,
        onSuccess: (contents: Content) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )
}