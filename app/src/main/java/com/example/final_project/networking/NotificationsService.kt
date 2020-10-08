package com.example.final_project.networking

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.final_project.R
import com.example.final_project.model.Notification
import com.example.final_project.ui.notifications.NotificationRecyclerAdapter
import com.google.gson.Gson

class NotificationsService private constructor() : IService {

    private val baseUrl: String = "https://api.nasa.gov/DONKI/notifications?startDate=START_DATE&endDate=END_DATE&type=all&api_key=DEMO_KEY"

    // Singleton pattern
    private object HOLDER { val INSTANCE = NotificationsService() }

    companion object {
        val instance: NotificationsService by lazy { HOLDER.INSTANCE }
    }

    override fun getData(view: View) {
        val queue = Volley.newRequestQueue(view.context)
        val url = setUrl()

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                val notifications = Gson().fromJson(response, Array<Notification>::class.java).toList()
                val recyclerView = view.findViewById<RecyclerView>(R.id.notificationRV)
                val layoutManager =  LinearLayoutManager(view.context)
                val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)

                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = NotificationRecyclerAdapter(notifications)
                recyclerView.addItemDecoration(dividerItemDecoration)
            },
            Response.ErrorListener { Log.d("NETWORK", "Problem getting notifications request") })

        queue.add(stringRequest)
    }

    override fun setUrl(): String {
        val startDate = "2014-05-01"
        val endDate = "2014-05-08"

        val baseUrl: String = "https://api.nasa.gov/DONKI/notifications?startDate=START_DATE&endDate=END_DATE&type=all&api_key=DEMO_KEY"
        var url = baseUrl.replace("START_DATE", startDate)
        url = url.replace("END_DATE", endDate)

        return url
    }
}