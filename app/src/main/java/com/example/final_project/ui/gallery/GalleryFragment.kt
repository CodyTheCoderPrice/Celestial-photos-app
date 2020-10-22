package com.example.final_project.ui.gallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.final_project.APOD
import com.example.final_project.R
import com.squareup.picasso.Picasso

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)

        val titleTextView: TextView = root.findViewById(R.id.apod_title)
        val photoImageView: ImageView = root.findViewById(R.id.apod_IV)
        context?.let {
            APOD().getAPOD(it) { success, apodObject ->
                if (success) {
                    titleTextView.text = apodObject.title
                    if (apodObject.media_type == "image") {
                        Picasso.with(it).load(apodObject.url).into(photoImageView);
                    } else if (apodObject.media_type == "video") {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(apodObject.url)), null)
                    }
                } else {
                    titleTextView.text = "Didn't receive request..."
                }
            }
        }
        return root
    }
}