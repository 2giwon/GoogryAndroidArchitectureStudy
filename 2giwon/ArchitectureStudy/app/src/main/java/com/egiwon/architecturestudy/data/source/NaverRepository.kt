package com.egiwon.architecturestudy.data.source

import com.egiwon.architecturestudy.data.model.ContentSearchResult

interface NaverRepository {

    fun getContents(
        type: String,
        query: String
    ): ContentSearchResult
}