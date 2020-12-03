package com.example.final_project.model

import android.util.Log
import com.example.final_project.database.TodaysApodRepository
import java.text.SimpleDateFormat
import java.util.*

class TodaysApodModel(private val todaysApodRepository: TodaysApodRepository) {

    fun getTodaysApod(): TodaysApod? {
        return todaysApodRepository.getTodaysApod()
    }

    fun setTodaysApod(todaysApod: TodaysApod) {
        if (getTodaysApod() == null) {
            todaysApodRepository.addTodaysApod(todaysApod)
        } else {
            todaysApodRepository.updateTodaysApod(todaysApod)
        }

    }

    // TODO: Add Unit test for this
    fun haveTodaysApod(): Boolean {
        val todaysApod = getTodaysApod()
        Log.d("Here", "haveTodaysApod " + (todaysApod != null))
        if (todaysApod != null) {
            val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val todaysDate = fmt.format(Date())
            val currentApodDate = fmt.format(fmt.parse(todaysApod.date))
            Log.d("Here", todaysApod.date + " vs " + fmt.parse(todaysApod.date))
            Log.d("Here", fmt.parse(todaysDate).toString() + " vs " + fmt.parse(currentApodDate))
            return fmt.parse(todaysDate) == (fmt.parse(currentApodDate))
        } else {
            return false
        }
    }
}
