package com.example.final_project.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "streak")
data class Streak(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "streak") var streak: Int
)