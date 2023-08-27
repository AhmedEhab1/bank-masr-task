package com.stc.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historical_list")
data class HistoricalData(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val keyFrom : String,
    val valueFrom: String,
    val keyTo : String,
    val valueTo : Double
)