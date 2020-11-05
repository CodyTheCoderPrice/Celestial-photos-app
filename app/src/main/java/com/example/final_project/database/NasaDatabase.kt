package com.example.final_project.database

import androidx.room.*
import com.example.final_project.model.ApodObject
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
    fun addApodObject(apod: ApodObject)

    @Query("select * from apod")
    fun getApods(): List<ApodObject>
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

@Database(entities = arrayOf(Streak::class, ApodObject::class), version = 1, exportSchema = false)
@TypeConverters(UUIDConverter::class, DateConverter::class)
abstract class NasaDatabase : RoomDatabase() {
    abstract fun streakDao(): StreakDao
    abstract fun apodDao(): ApodDao
}