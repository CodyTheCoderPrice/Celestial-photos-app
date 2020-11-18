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

    fun addApod(Apod: Apod) {
        apodRepository.addApod(Apod)
    }
}