package com.example.final_project.database

import android.content.Context
import androidx.room.Room
import com.example.final_project.model.Apod
import com.example.final_project.ui.apod.ApodFragment

class ApodRepository(ctx: Context) {
    private val apods: MutableList<Apod> = mutableListOf()
    private val db: NasaDatabase = Room.databaseBuilder(ctx, NasaDatabase::class.java, "apods.db")
        .build()

    fun addApod(apod: Apod) {
        db.apodDao().addApod(apod)
        apods.add(apod)
    }

    fun getApods(): List<Apod> {
        return apods
    }

    fun getApod(idx: Int): Apod {
        return apods[idx]
    }
}