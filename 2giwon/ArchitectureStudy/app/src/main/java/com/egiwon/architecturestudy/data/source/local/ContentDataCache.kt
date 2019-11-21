package com.egiwon.architecturestudy.data.source.local

import androidx.paging.DataSource
import com.egiwon.architecturestudy.data.model.Content
import java.util.concurrent.Executor

class ContentDataCache(
    private val contentDao: ContentDao,
    private val ioExecutor: Executor
) {

    fun insert(contents: List<Content>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            contentDao.insert(contents)
            insertFinished()
        }
    }

    fun contentsByTitle(title: String): DataSource.Factory<Int, Content> {
        val query = title.trim()
        return contentDao.contents(query)
    }
}