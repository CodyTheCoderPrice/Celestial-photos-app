package com.example.final_project.ui.earth

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.final_project.R
import com.example.final_project.model.Earth
import com.example.final_project.networking.EarthService
import com.google.gson.Gson
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class EarthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_earth, container, false)

        root.findViewById<Button>(R.id.getEarthImageBtn)?.setOnClickListener { EarthService.instance.getData(root, ::openPopup) }
        root.findViewById<Button>(R.id.todayBtn)?.setOnClickListener {
            val date = Calendar.getInstance()
            val text = "${date.get(Calendar.DATE)}/${date.get(Calendar.MONTH)+1}/${date.get(Calendar.YEAR)}"
            root.findViewById<EditText>(R.id.dateET)?.setText(text)
        }

        return root
    }

    /**
     * Opens the popup of the image from the EARTH API
     */
    private suspend fun openPopup(view: View, response: String) {
        val earth = Gson().fromJson(response, Earth::class.java)

        withContext(Dispatchers.Main) {
            val dialog = Dialog(view.context)
            dialog.setContentView(R.layout.image_dialog)
            val image = dialog.findViewById<ImageView>(R.id.image)
            Picasso.with(view.context).load(earth.url).into(image, object : Callback {
                override fun onSuccess() {
                    dialog.show()
                }

                override fun onError() {
                    Toast.makeText(
                        view.context,
                        "There was a problem loading the image, please try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}