package com.example.final_project.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.final_project.ApodObject
import com.example.final_project.R

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

        /*val textView: TextView = root.findViewById(R.id.url_header_tv)
        val imageView: ImageView = root.findViewById(R.id.apod_IV)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            activity?.applicationContext?.let { it1 -> APOD().getAPOD(it1, textView, imageView) }
        })*/
        return root
    }
}