package com.example.final_project.networking

import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.final_project.R
import com.example.final_project.networking.helper.checkValidDate
import com.example.final_project.networking.helper.checkValidLatLong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class EarthService: IService {

    private val scope = CoroutineScope(Dispatchers.Main)
    private val baseURL = "https://api.nasa.gov/planetary/earth/assets?lat=LAT_VAL&lon=LONG_VAL&date=DATE_VAL&api_key=DEMO_KEY"
    private var lat = "0.0"
    private var long = "0.0"
    private var date = "2020-10-31"

    // Singleton pattern
    private object HOLDER { val INSTANCE = EarthService() }

    companion object {
        val instance: EarthService by lazy { HOLDER.INSTANCE }
    }

    override fun getData(view: View, callback: suspend (view: View, response: String) -> Unit) {
        try {
            view.findViewById<ProgressBar>(R.id.earthProgressBar).visibility = View.VISIBLE
            getViewData(view)
            val url = setUrl()

            val queue = Volley.newRequestQueue(view.context)
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener { response ->
                    scope.launch(Dispatchers.Default) { callback(view, response) }
                },
                Response.ErrorListener {
                    scope.launch(Dispatchers.Main) {
                        view.findViewById<ProgressBar>(R.id.earthProgressBar).visibility = View.INVISIBLE
                        Toast.makeText(
                            view.context,
                            "There was a problem getting the EARTH API Image, please try again later",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })

            queue.add(stringRequest)
        } catch (e: Exception) {
            Toast.makeText(view.context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Sets the URL with the date, latitude, and longitude data
     */
    override fun setUrl(): String {
        var url = baseURL.replace("LAT_VAL", lat)
        url = url.replace("LONG_VAL", long)
        url = url.replace("DATE_VAL", date)
        return url
    }

    /**
     * Gets the data from the view
     */
    private fun getViewData(view: View) {
        val sdf = SimpleDateFormat("dd/MM/yyyy")

        // Get the edit text views
        val date = view.findViewById<EditText>(R.id.dateET).text.toString()
        val lat = view.findViewById<EditText>(R.id.latET).text.toString()
        val long = view.findViewById<EditText>(R.id.longET).text.toString()

        // Validate and set latitude/longitude
        checkValidLatLong(lat, long)
        this.lat = lat
        this.long = long

        // Validate and set the date
        checkValidDate(date)
        val cal = Calendar.getInstance()
        cal.time = sdf.parse(date)
        this.date = "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)+1}-${cal.get(Calendar.DATE)}"
    }
}