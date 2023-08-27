package com.stc.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stc.domain.entity.HistoricalData

@Dao
interface HistoricalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPosts(product: List<HistoricalData>)

    @Query("SELECT * FROM historical_list")
    suspend fun getPosts(): List<HistoricalData>
}