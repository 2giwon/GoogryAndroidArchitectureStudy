package com.egiwon.architecturestudy.data.source.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egiwon.architecturestudy.data.model.Content

@Dao
interface ContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Content>)

    @Query(
        "SELECT * FROM content WHERE (query LIKE :queryString)" +
                "OR (title LIKE :queryString) ORDER BY title ASC"
    )
    fun contents(queryString: String): DataSource.Factory<Int, Content>

}