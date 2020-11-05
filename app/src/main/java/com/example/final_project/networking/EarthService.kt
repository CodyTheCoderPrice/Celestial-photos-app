package com.example.final_project.networking

import android.app.Dialog
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.final_project.R
import com.example.final_project.model.Earth
import com.example.final_project.model.EarthResource
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class EarthService: IService {

    private val baseURL = "https://api.nasa.gov/planetary/earth/assets?lat=LAT_VAL&lon=LONG_VAL&date=DATE_VAL&api_key=DEMO_KEY"
    private var lat = "0.0"
    private var long = "0.0"
    private var date = "2020-10-31"

    // Singleton pattern
    private object HOLDER { val INSTANCE = EarthService() }

    companion object {
        val instance: EarthService by lazy { HOLDER.INSTANCE }
    }

    override fun getData(view: View) {
        getViewData(view)
        val url = setUrl()

        val queue = Volley.newRequestQueue(view.context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                val earth = Gson().fromJson(response, Earth::class.java)
                openPopup(view, earth)
            },
            Response.ErrorListener {
                Toast.makeText(view.context, "There was a problem getting the EARTH API Image, please try again later", Toast.LENGTH_LONG).show()
            })

        queue.add(stringRequest)
    }

    override fun setUrl(): String {
        var url = baseURL.replace("LAT_VAL", lat)
        url = url.replace("LONG_VAL", long)
        url = url.replace("DATE_VAL", date)
        return url
    }

    private fun openPopup(view: View, earth: Earth) {
        val dialog = Dialog(view.context)
        dialog.setContentView(R.layout.image_dialog)
        val image = dialog.findViewById<ImageView>(R.id.image)
        Picasso.with(view.context).load(earth.url).into(image)
        dialog.show()
    }

    /**
     * Gets the data from the view
     */
    private fun getViewData(view: View) {
        val sdf = SimpleDateFormat("dd/MM/yyyy")

        val date = view.findViewById<EditText>(R.id.dateET).text.toString()
        val lat = view.findViewById<EditText>(R.id.latET).text.toString()
        val long = view.findViewById<EditText>(R.id.longET).text.toString()

        checkValidLatLong(lat, long)
        this.lat = lat
        this.long = long

        checkValidDate(date)
        val cal = Calendar.getInstance()
        cal.time = sdf.parse(date)
        this.date = "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)+1}-${cal.get(Calendar.DATE)}"
    }

    /**
     * Checks if date is valid
     */
    private fun checkValidDate(date: String) {
        if (date.isNullOrEmpty()) {
            throw Exception("Date must be filled in")
        }
    }

    /**
     * Checks if latitude and longitude are valid numbers
     */
    private fun checkValidLatLong(lat: String, long: String) {
        if (lat.isNullOrEmpty() || long.isNullOrEmpty()) {
            throw Exception("Latitude or Longitude must be filled in")
        }

        val lat = lat.toFloat()
        val long = long.toFloat()
        if (lat < -90 || lat > 90) {
            throw Exception("Latitude is not valid")
        }
        if (long < -180 || long > 180) {
            throw Exception("Longitude is not valid")
        }
    }
}