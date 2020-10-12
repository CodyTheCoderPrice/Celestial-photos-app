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
    fun getAPOD(context: Context, textView: TextView, imageView: ImageView) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY"

        val gson = Gson()

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val apodObject: ApodObject = gson.fromJson(response, ApodObject::class.java)
                textView.text = apodObject.url

//                if (apodObject.media_type == "image") {
//                    Picasso.with(context).load(apodObject.url).into(imageView);
//                } else if (apodObject.media_type == "video") {
                
                    startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")), null)

                    //val intent: Intent = Intent(Intent.ACTION_VIEW)
                    //intent.setDataAndType(Uri.parse("https://www.youtube.com/watch?v=DzrCEm1ZBRY"), "video/*")
                    //startActivity(context, Intent.createChooser(intent, "Complete action using"), null)
                //}

            },
            { textView.text = "Request didn't work" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}