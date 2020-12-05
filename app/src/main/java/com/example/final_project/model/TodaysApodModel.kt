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
        if (todaysApod != null) {
            val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val todaysDate = fmt.format(Date())
            val currentApodDate = fmt.format(fmt.parse(todaysApod.date))
            return fmt.parse(todaysDate) == (fmt.parse(currentApodDate))
        } else {
            return false
        }
    }
}
