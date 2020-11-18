package com.example.final_project.networking

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.final_project.model.Apod
import com.google.gson.Gson
import java.util.*


class APOD {
    fun getAPOD(context: Context, callback: (Boolean, Apod) -> Unit) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY"

        val gson = Gson()

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val apod: Apod = gson.fromJson(response, Apod::class.java)
                callback(true, apod)
            },
            { callback(false,
                Apod(
                    UUID.randomUUID(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            ) })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}