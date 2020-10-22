package com.example.final_project.networking

import android.view.View
import android.widget.Toast
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
import java.util.*

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
            Response.ErrorListener {
                Toast.makeText(view.context, "There was a problem getting notifications, please try again later", Toast.LENGTH_LONG).show()
            })

        queue.add(stringRequest)
    }

    override fun setUrl(): String {
        val calendar = Calendar.getInstance()
        val endDate = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH)}-${calendar.get(Calendar.DAY_OF_MONTH)}"

        calendar.add(Calendar.DATE, -7) // Get the date from a week ago
        val startDate = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH)}-${calendar.get(Calendar.DAY_OF_MONTH)}"

        var url = baseUrl.replace("START_DATE", startDate)
        url = url.replace("END_DATE", endDate)

        return url
    }
}