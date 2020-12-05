package com.example.final_project.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "favoriteApod")
class FavoriteApod(
    @PrimaryKey var id: UUID,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "explanation") val explanation: String,
    @ColumnInfo(name = "media_type") val media_type: String,
    @ColumnInfo(name = "service_version") val service_version: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String
) {
    override fun toString(): String {
        return "[date: ${this.date}, explanation: ${this.explanation}, media_type: ${this.media_type}, service_version: ${this.service_version}, title: ${this.title}, url: ${this.url}]"
    }
}