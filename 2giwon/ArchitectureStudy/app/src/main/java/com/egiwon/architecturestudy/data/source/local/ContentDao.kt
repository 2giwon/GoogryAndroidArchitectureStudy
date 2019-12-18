package com.egiwon.architecturestudy.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egiwon.architecturestudy.data.source.local.model.Content
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface ContentDao {

    @Query(
        "SELECT * FROM contents WHERE (type LIKE :type) ORDER BY id ASC"
    )
    fun getContentCache(type: String): Maybe<List<Content>>

    @Query(
        "SELECT * FROM contents WHERE (type LIKE :type) AND " +
                "(searchQuery LIKE :query) ORDER BY id DESC"
    )
    fun getContent(type: String, query: String): Maybe<Content>

    @Query("SELECT searchTime FROM contents WHERE (type LIKE :type) ORDER BY id ASC")
    fun getSearchTime(type: String): Maybe<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContent(content: Content): Completable

    @Query("DELETE FROM contents WHERE (type LIKE :type) AND (searchQuery LIKE :query)")
    fun deleteContents(type: String, query: String): Completable

}