package com.example.final_project.database

import android.content.Context
import androidx.room.Room
import com.example.final_project.model.FavoriteApod

class FavoriteApodRepository(ctx: Context) {
    private var favoriteApods: MutableList<FavoriteApod> = mutableListOf()
    private val db: NasaDatabase = Room.databaseBuilder(ctx, NasaDatabase::class.java, "favoriteApods.db")
        .build()

    init {
        favoriteApods.addAll(db.favoriteApodDao().getFavoriteApods())
    }

    fun addFavoriteApod(favoriteApod: FavoriteApod) {
        db.favoriteApodDao().addFavoriteApod(favoriteApod)
        favoriteApods.add(favoriteApod)
    }

    fun getFavoriteApods(): List<FavoriteApod> {
        return favoriteApods
    }
}