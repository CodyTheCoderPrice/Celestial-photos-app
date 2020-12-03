package com.example.final_project.database

import androidx.room.*
import androidx.room.Dao
import com.example.final_project.model.FavoriteApod
import com.example.final_project.model.TodaysApod
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
interface FavoriteApodDao {
    @Insert
    fun addFavoriteApod(favoriteApod: FavoriteApod)

    @Query("select * from favoriteApod")
    fun getFavoriteApods(): List<FavoriteApod>
}

@Dao
interface TodaysApodDao {
    @Insert
    fun addTodaysApod(todaysApod: TodaysApod)

    @Update
    fun updateTodaysApod(todaysApod: TodaysApod)

    @Query("select * from todaysApod LIMIT 1")
    fun getTodaysApod(): TodaysApod
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

@Database(entities = arrayOf(Streak::class, FavoriteApod::class, TodaysApod::class), version = 1, exportSchema = false)
@TypeConverters(UUIDConverter::class, DateConverter::class)
abstract class NasaDatabase : RoomDatabase() {
    abstract fun streakDao(): StreakDao
    abstract fun favoriteApodDao(): FavoriteApodDao
    abstract fun todaysApodDao(): TodaysApodDao
}