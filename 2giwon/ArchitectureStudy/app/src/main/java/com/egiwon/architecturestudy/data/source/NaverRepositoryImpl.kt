package com.egiwon.architecturestudy.data.source

import androidx.paging.LivePagedListBuilder
import com.egiwon.architecturestudy.data.model.ContentSearchResult
import com.egiwon.architecturestudy.data.source.local.ContentDataCache
import com.egiwon.architecturestudy.data.source.remote.NaverRemoteDataSource

class NaverRepositoryImpl(
    private val naverRemoteDataSource: NaverRemoteDataSource,
    private val cache: ContentDataCache
) : NaverRepository {

    override fun getContents(
        type: String,
        query: String
    ): ContentSearchResult {

        val boundaryCallback = ContentBoundaryCallback(
            type,
            query,
            cache,
            naverRemoteDataSource
        )

        val contentDataFactory = cache.contentsByTitle(query)

        val livePagedListBuilder = LivePagedListBuilder(contentDataFactory, LOAD_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return ContentSearchResult(
            livePagedListBuilder,
            boundaryCallback.networkErrors
        )
    }


    companion object {
        private var instance: NaverRepositoryImpl? = null

        fun getInstance(
            remoteDataSource: NaverRemoteDataSource,
            cache: ContentDataCache
        ): NaverRepositoryImpl =

            instance ?: NaverRepositoryImpl(remoteDataSource, cache).apply {
                instance = this
            }

        const val LOAD_PAGE_SIZE = 10
    }

}
