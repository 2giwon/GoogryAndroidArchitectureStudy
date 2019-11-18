package com.egiwon.architecturestudy.data.source

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.egiwon.architecturestudy.data.Content
import com.egiwon.architecturestudy.data.ContentDataFactory

class NaverRepositoryImpl : NaverRepository {

    private val config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(20)
        .setPageSize(LOAD_PAGE_SIZE)
        .setPrefetchDistance(5)
        .setEnablePlaceholders(false)
        .build()

    override fun getContents(
        type: String,
        query: String
    ): ContentSearchResult {
//        val boundaryCallback = ContentBoundaryCallback(
//            type,
//            query,
//            naverRemoteDataSource
//        )

        val contentDataFactory = ContentDataFactory(type, query)

        val livePagedListBuilder = LivePagedListBuilder<Int, Content.Item>(
            contentDataFactory, config
        ).build()


        return ContentSearchResult(livePagedListBuilder, Throwable())
    }


    companion object {
        private var instance: NaverRepositoryImpl? = null

        fun getInstance(): NaverRepositoryImpl =
            instance ?: NaverRepositoryImpl().apply {
                instance = this
            }

        private const val LOAD_PAGE_SIZE = 10
    }

}