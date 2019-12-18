package com.egiwon.architecturestudy.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.egiwon.architecturestudy.data.source.remote.response.ContentItem

@Entity(tableName = "contents")
data class Content(
    val list: List<ContentItem>,
    val type: String,
    val searchQuery: String = "",
    val searchTime: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {


    companion object {
        fun empty(type: String, query: String): Content =
            Content(emptyList(), type, query, System.currentTimeMillis())
    }
}