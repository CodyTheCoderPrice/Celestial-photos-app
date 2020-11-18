package com.example.final_project.model

import android.util.Log
import com.example.final_project.database.ApodRepository

class ApodModel(private val apodRepository: ApodRepository) {
    
    fun getApods(): List<Apod> {
        return apodRepository.getApods()
    }

    fun getApod(idx: Int): Apod {
        return apodRepository.getApod(idx)
    }

    fun addApod(apod: Apod) {
        apodRepository.addApod(apod)
    }

    fun containsApod(apodCheck: Apod): Boolean {
        val apods = getApods()
        val iterator = apods.listIterator()

        for (apod in iterator) {
            if (apod.url == apodCheck.url) {
                return true
            }
        }

        return false
    }
}