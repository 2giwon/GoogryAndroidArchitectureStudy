package com.egiwon.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egiwon.local.model.Content

@Dao
interface ContentDao {

    @Query("SELECT * FROM contents WHERE (type LIKE :type) ORDER BY id DESC LIMIT 1")
    suspend fun getContentCache(type: String): Content

    @Query("SELECT `query` FROM contents WHERE (type LIKE :type) ORDER BY id DESC")
    suspend fun getContentQuery(type: String): List<String>

    @Query("SELECT * FROM contents WHERE (type LIKE :type) AND (`query` LIKE :query)")
    suspend fun getContents(type: String, query: String): Content

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(content: Content)

}