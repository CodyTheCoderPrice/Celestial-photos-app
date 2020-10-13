package com.example.final_project

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.squareup.picasso.Picasso


class APOD {
    fun getAPOD(context: Context, callback: (Boolean, ApodObject) -> Unit) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY"

        val gson = Gson()

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val apodObject: ApodObject = gson.fromJson(response, ApodObject::class.java)
                callback(true, apodObject)
            },
            { callback(false, ApodObject("", "", "", "", "", "")) })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}