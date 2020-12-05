package com.example.final_project.service

import androidx.lifecycle.MutableLiveData
import com.example.final_project.model.TodaysApod

class MessageQueue {

    companion object {
        val Channel: MutableLiveData<TodaysApod> = MutableLiveData()
    }
}