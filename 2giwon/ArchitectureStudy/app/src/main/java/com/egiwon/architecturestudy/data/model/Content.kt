package com.egiwon.architecturestudy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ContentSearchResponse(
    @SerializedName("total")
    val total: Int,
    @SerializedName("start")
    val start: Int,
    @SerializedName("display")
    val display: Int,
    @SerializedName("items")
    val items: List<Content>
)

@Entity(tableName = "content")
data class Content(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id") var id: Long,
    @field:SerializedName("query") var query: String?,
    @field:SerializedName("image")
    val image: String?,
    @field:SerializedName("actor")
    val actor: String?,
    @field:SerializedName("description")
    val description: String?,
    @field:SerializedName("link")
    val link: String?,
    @field:SerializedName("title")
    val title: String?
)