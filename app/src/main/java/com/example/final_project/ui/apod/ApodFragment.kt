package com.example.final_project.ui.apod

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.final_project.R
import com.example.final_project.database.FavoriteApodRepository
import com.example.final_project.database.TodaysApodRepository
import com.example.final_project.model.FavoriteApod
import com.example.final_project.model.FavoriteApodModel
import com.example.final_project.model.TodaysApod
import com.example.final_project.model.TodaysApodModel
import com.example.final_project.networking.apodApi
import com.example.final_project.service.ApodNotificationService
import com.example.final_project.service.MessageQueue
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_apod, container, false)

        val titleTextView: TextView = root.findViewById(R.id.apod_title)
        val viewFavoriteBtn: Button = root.findViewById(R.id.apod_view_favorite_btn)
        viewFavoriteBtn.setOnClickListener {
            root.findNavController().navigate(R.id.nav_all_apods)
        }
        context?.let {
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch(Dispatchers.Default) {
                var todaysApod: TodaysApod? = null
                var haveTodaysApod = false

                withContext(Dispatchers.IO) {
                    val todaysApodModel = TodaysApodModel(TodaysApodRepository(it))
                    todaysApod = todaysApodModel.getTodaysApod()
                    haveTodaysApod = todaysApodModel.haveTodaysApod()

                    Log.d("Here", "apodFragment " + todaysApod.toString())

                    withContext(Dispatchers.Main) {
                        if (todaysApod != null && haveTodaysApod) {
                            setApodView(root, todaysApod!!, haveTodaysApod)
                        } else {
                            withContext(Dispatchers.IO) {
                                apodApi()
                                    .getAPOD(it) { success, apod ->
                                        if (success) {
                                            Log.d(
                                                "Here",
                                                "apodFragment, didn't have todaysApod, so fetched it " + apod.toString()
                                            )

                                            setApodView(root, apod!!, true)

                                            scope.launch(Dispatchers.Default) {
                                                withContext(Dispatchers.IO) {
                                                    todaysApodModel.setTodaysApod(apod)
                                                }
                                            }

                                        } else {
                                            titleTextView.text = "Didn't receive request..."

                                            MessageQueue.Channel.observe(viewLifecycleOwner,
                                                Observer<TodaysApod> { apod ->
                                                    Log.d("Here", "apodFragmentMessageQueue $apod")

                                                    scope.launch(Dispatchers.Default) {
                                                        withContext(Dispatchers.Main) {
                                                            if (apod != null) {
                                                                setApodView(root, apod!!, true)
                                                            }
                                                        }
                                                    }
                                                })
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        }
        return root
    }

    private fun setApodView(view: View, todaysApod: TodaysApod, haveTodaysApod: Boolean) {
        val titleTextView: TextView = view.findViewById(R.id.apod_title)
        val photoImageView: ImageView = view.findViewById(R.id.apod_IV)
        val favoriteBtn: Button = view.findViewById(R.id.apod_favorite_btn)

        titleTextView.text = todaysApod.title
        if (todaysApod.media_type == "image") {
            Picasso.with(view.context).load(todaysApod.url).into(photoImageView);
        } else if (todaysApod.media_type == "video") {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(todaysApod.url)),
                null
            )
        }

        favoriteBtn.setOnClickListener {
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch(Dispatchers.Default) {
                val favoriteApodModel =
                    FavoriteApodModel(FavoriteApodRepository(view.context))
                val favoriteApod = FavoriteApod(
                    todaysApod.id,
                    todaysApod.date,
                    todaysApod.explanation,
                    todaysApod.media_type,
                    todaysApod.service_version,
                    todaysApod.title,
                    todaysApod.url
                )

                if (!favoriteApodModel.containsFavoriteApod(favoriteApod)) {
                    favoriteApodModel.addFavoriteApod(favoriteApod)
                    activity!!.runOnUiThread {
                        Toast.makeText(
                            activity,
                            "Favorited",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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
    }
}