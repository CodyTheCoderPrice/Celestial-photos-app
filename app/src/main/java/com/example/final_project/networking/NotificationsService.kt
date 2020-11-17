package com.example.final_project.networking

import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class NotificationsService private constructor() : IService {

    private val scope = CoroutineScope(Dispatchers.Main)
    private val baseUrl: String = "https://api.nasa.gov/DONKI/notifications?startDate=START_DATE&endDate=END_DATE&type=all&api_key=DEMO_KEY"

    // Singleton pattern
    private object HOLDER { val INSTANCE = NotificationsService() }

    companion object {
        val instance: NotificationsService by lazy { HOLDER.INSTANCE }
    }

    /**
     * Makes a volley request and calls the callback function with the result
     */
    override fun getData(view: View, callback: suspend (view: View, response: String) -> Unit) {
        val queue = Volley.newRequestQueue(view.context)
        val url = setUrl()

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                scope.launch(Dispatchers.Default) { callback(view, response) }
            },
            Response.ErrorListener {
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(view.context, "There was a problem getting notifications, please try again later", Toast.LENGTH_LONG).show()
                }
            })

        // TODO: Check if this is done on a background thread
        queue.add(stringRequest)
    }

    /**
     * Gets the most recent date and builds the url to make the request with
     */
    override fun setUrl(): String {
        // Get today's date
        val calendar = Calendar.getInstance()
        val endDate = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH)}-${calendar.get(Calendar.DAY_OF_MONTH)}"

        // Get the date from a week ago
        calendar.add(Calendar.DATE, -7)
        val startDate = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH)}-${calendar.get(Calendar.DAY_OF_MONTH)}"

        var url = baseUrl.replace("START_DATE", startDate)
        url = url.replace("END_DATE", endDate)

        return url
    }
}