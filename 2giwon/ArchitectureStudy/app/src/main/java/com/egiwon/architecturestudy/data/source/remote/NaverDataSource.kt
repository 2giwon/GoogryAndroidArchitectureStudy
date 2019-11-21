package com.egiwon.architecturestudy.data.source.remote

import com.egiwon.architecturestudy.data.model.Content


interface NaverDataSource {

    fun getContents(
        type: String,
        query: String,
        page: String,
        display: String,
        onSuccess: (contents: List<Content>) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )
}