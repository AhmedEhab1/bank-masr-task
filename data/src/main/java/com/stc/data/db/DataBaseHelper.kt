package com.stc.data.db

import android.content.Context
import com.stc.domain.entity.HistoricalData

object DataBaseHelper {
    suspend fun savaPostsList(context: Context, list: List<HistoricalData>) {
        val historicalData: List<HistoricalData> = list
        val db = HistoricalDataBase.getDatabase(context)
        db.postDao().insertPosts(historicalData)
    }

    suspend fun getPostsFromDb(context: Context): List<HistoricalData> {
        val db = HistoricalDataBase.getDatabase(context)
        return db.postDao().getPosts()
    }
}