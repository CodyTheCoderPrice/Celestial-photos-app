package com.example.final_project.ui.apod

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.final_project.R
import com.example.final_project.database.ApodRepository
import com.example.final_project.model.ApodModel
import com.example.final_project.networking.APOD
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_apod, container, false)

        val titleTextView: TextView = root.findViewById(R.id.apod_title)
        val photoImageView: ImageView = root.findViewById(R.id.apod_IV)
        val favoriteBtn: Button = root.findViewById(R.id.apod_favorite_btn)
        val viewFavoriteBtn: Button = root.findViewById(R.id.apod_view_favorite_btn)
        viewFavoriteBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, AllApodsFragment.newInstance())
                .commit()
        }
        context?.let {
            APOD()
                .getAPOD(it) { success, apod ->
                if (success) {
                    titleTextView.text = apod.title
                    if (apod.media_type == "image") {
                        Picasso.with(it).load(apod.url).into(photoImageView);
                    } else if (apod.media_type == "video") {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(apod.url)), null)
                    }

                    favoriteBtn.setOnClickListener {
                        val scope = CoroutineScope(Dispatchers.Main)
                        scope.launch(Dispatchers.Default) {
                            val apodModel = ApodModel(ApodRepository(root.context))

                            if (!apodModel.containsApod(apod)) {
                                apodModel.addApod(apod)
                            } else {
                                activity!!.runOnUiThread {
                                    Toast.makeText(
                                        activity,
                                        "Apod already favorted",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                } else {
                    titleTextView.text = "Didn't receive request..."
                }
            }
        }
        return root
    }
}