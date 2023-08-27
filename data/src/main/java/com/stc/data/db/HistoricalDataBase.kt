package com.stc.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stc.domain.entity.HistoricalData

@Database(entities = [HistoricalData::class], version = 1)
abstract class HistoricalDataBase : RoomDatabase() {
    abstract fun postDao(): HistoricalDao

    companion object {
        @Volatile
        private var INSTANCE: HistoricalDataBase? = null

        fun getDatabase(context: Context): HistoricalDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoricalDataBase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}