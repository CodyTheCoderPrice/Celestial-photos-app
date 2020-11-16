package com.example.final_project.networking

import android.view.View

interface IService {
    fun getData(view: View, callback: suspend (view: View, response: String) -> Unit)
    fun setUrl(): String
}