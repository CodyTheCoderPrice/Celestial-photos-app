package com.example.final_project

import android.content.Context
import android.graphics.drawable.Drawable
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.io.InputStream
import java.net.URL


class APOD {
    fun getAPOD(context: Context?, textView: TextView, imageView: ImageView) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY"

        val gson = Gson()

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val apodObject: ApodObject = gson.fromJson(response, ApodObject::class.java)
                Log.i("Here", apodObject.url)
                textView.text = apodObject.url

                if (apodObject.media_type == "image") {
                    Picasso.with(context).load(apodObject.url).into(imageView);
                }

            },
            { textView.text = "Request didn't work" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}