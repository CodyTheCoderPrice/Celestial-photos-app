package com.example.final_project.networking

import android.os.AsyncTask
import com.example.final_project.model.Notification
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.HttpURLConnection
import java.net.URL

class NotificationsService private constructor(): AsyncTask<String, Void, List<Notification>>() {

    private val baseUrl: String = "https://api.nasa.gov/DONKI/notifications?startDate=START_DATE&endDate=END_DATE&type=all&api_key=DEMO_KEY"

    // Singleton pattern
    private object HOLDER { val INSTANCE = NotificationsService() }

    companion object {
        val instance: NotificationsService by lazy { HOLDER.INSTANCE }
    }

    private fun setUrl(startDate: String, endDate: String): URL {
        var url = baseUrl.replace("START_DATE", startDate)
        url = url.replace("END_DATE", endDate)
        return URL(url)
    }

    override fun doInBackground(vararg p0: String): List<Notification> {
        val start = p0[0]
        val end = p0[1]
        val url = setUrl(start, end)
        val content = url.readText()
        return Gson().fromJson(content, Array<Notification>::class.java).toList()
    }
}