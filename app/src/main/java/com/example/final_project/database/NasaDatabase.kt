package com.example.final_project.database

import androidx.room.*
import androidx.room.Dao
import com.example.final_project.model.Apod
import com.example.final_project.model.Streak
import java.util.*

@Dao
interface StreakDao {
    @Insert
    fun addStreak(streak: Streak)

    @Update
    fun updateStreak(streak: Streak)

    @Query("select * from streak")
    fun getStreak(): List<Streak>
}

@Dao
interface ApodDao {
    @Insert
    fun addApod(apod: Apod)

    @Query("select * from apod")
    fun getApods(): List<Apod>
}

class DateConverter {
    @TypeConverter
    fun fromString(date: String): Date {
        return Date(date)
    }

    @TypeConverter
    fun toString(date: Date): String {
        return date.toString()
    }
}

class UUIDConverter {
    @TypeConverter
    fun fromString(uuid: String): UUID {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun toString(uuid: UUID): String {
        return uuid.toString()
    }
}

@Database(entities = arrayOf(Streak::class, Apod::class), version = 1, exportSchema = false)
@TypeConverters(UUIDConverter::class, DateConverter::class)
abstract class NasaDatabase : RoomDatabase() {
    abstract fun streakDao(): StreakDao
    abstract fun apodDao(): ApodDao
}