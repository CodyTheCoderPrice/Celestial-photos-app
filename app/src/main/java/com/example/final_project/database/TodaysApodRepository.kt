package com.example.final_project.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.final_project.model.TodaysApod

class TodaysApodRepository(ctx: Context) {
    private var todaysApodObj : TodaysApod?
    private val db: NasaDatabase = Room.databaseBuilder(ctx, NasaDatabase::class.java, "notificationApods.db")
        .build()

    init {
        todaysApodObj = db.todaysApodDao().getTodaysApod()
    }

    fun addTodaysApod(todaysApod: TodaysApod) {
        db.todaysApodDao().addTodaysApod(todaysApod)
        todaysApodObj = todaysApod
    }

    fun updateTodaysApod(todaysApod: TodaysApod) {
        db.todaysApodDao().updateTodaysApod(todaysApod)
        todaysApodObj = todaysApod
        Log.d("Here", "Updated --> ${getTodaysApod()}")

    }

    fun getTodaysApod(): TodaysApod? {
        return todaysApodObj
    }
}