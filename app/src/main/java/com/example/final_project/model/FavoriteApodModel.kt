package com.example.final_project.model

import com.example.final_project.database.FavoriteApodRepository

class FavoriteApodModel(private val favoriteApodRepository: FavoriteApodRepository) {
    
    fun getFavoriteApods(): List<FavoriteApod> {
        return favoriteApodRepository.getFavoriteApods()
    }

    fun addFavoriteApod(favoriteApod: FavoriteApod) {
        favoriteApodRepository.addFavoriteApod(favoriteApod)
    }

    // TODO: Add Unit test for this
    fun containsFavoriteApod(favoriteApodCheck: FavoriteApod): Boolean {
        val apods = getFavoriteApods()
        val iterator = apods.listIterator()

        for (apod in iterator) {
            if (apod.url == favoriteApodCheck.url) {
                return true
            }
        }

        return false
    }
}