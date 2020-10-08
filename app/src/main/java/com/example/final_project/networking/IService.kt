package com.example.final_project.networking

import android.view.View

interface IService {
    fun getData(view: View)
    fun setUrl(): String
}