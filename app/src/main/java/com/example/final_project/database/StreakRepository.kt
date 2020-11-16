package com.example.final_project.database

import android.content.Context
import androidx.room.Room
import com.example.final_project.model.Streak
import java.text.SimpleDateFormat
import java.util.*

class StreakRepository(ctx: Context) {
    private val streaks: MutableList<Streak> = mutableListOf()
    private val db: NasaDatabase = Room.databaseBuilder(ctx, NasaDatabase::class.java, "streaks.db")
        .build()

    init {
        clear()
    }

    fun addStreak(streak: Streak) {
        db.streakDao().addStreak(streak)
        streaks.add(streak)
    }

    fun updateStreak(streak: Streak) {
        db.streakDao().updateStreak(streak)
        clear()
    }

    fun getStreaks(): List<Streak> {
        return streaks
    }

    fun getRecentStreak(): Streak? {
        var recent: Streak? = null

        streaks.forEach {
            if (recent == null) {
                recent = it
            } else if (compareDates(recent!!.date, it.date)) {
                recent = it
            }
        }

        return recent
    }

    /**
     * Returns true if date 1 is before date 2, false otherwise
     */
    fun compareDates(date1: Date, date2: Date): Boolean {
        // Used to ignore seconds/hours/minutes
        val fmt = SimpleDateFormat("yyyyMMdd")
        val d1 = fmt.format(date1)
        val d2 = fmt.format(date2)
        return fmt.parse(d1).before(fmt.parse(d2))
    }

    fun setStreaks(streaks: List<Streak>) {
        this.streaks.clear()
        this.streaks.addAll(streaks)
    }

    private fun clear() {
        setStreaks(db.streakDao().getStreak())
    }
}