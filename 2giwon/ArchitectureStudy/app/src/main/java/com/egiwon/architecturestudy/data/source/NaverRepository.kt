package com.egiwon.architecturestudy.data.source

interface NaverRepository {

    fun getContents(
        type: String,
        query: String
    ): ContentSearchResult
}