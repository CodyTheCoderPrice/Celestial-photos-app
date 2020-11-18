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
import androidx.fragment.app.Fragment
import com.example.final_project.networking.APOD
import com.example.final_project.R
import com.example.final_project.database.ApodRepository
import com.example.final_project.model.ApodModel
import com.squareup.picasso.Picasso

class ApodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_apod, container, false)

        val apodModel = ApodModel(ApodRepository(root.context))

        val titleTextView: TextView = root.findViewById(R.id.apod_title)
        val photoImageView: ImageView = root.findViewById(R.id.apod_IV)
        val favoriteBtn: Button = root.findViewById(R.id.apod_favorite_btn)
        val viewFavoriteBtn: Button = root.findViewById(R.id.apod_view_favorite_btn)
        viewFavoriteBtn.setOnClickListener {
            val allApodsFragment = AllApodsFragment()
            childFragmentManager.beginTransaction().replace(R.id.action_container,allApodsFragment).commit()
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

                    favoriteBtn.visibility = Button.VISIBLE
                    favoriteBtn.setOnClickListener {
                        apodModel.addApod(apod)
                        favoriteBtn.visibility = Button.INVISIBLE
                    }
                } else {
                    titleTextView.text = "Didn't receive request..."
                }
            }
        }
        return root
    }
}