package com.example.final_project.ui.notifications

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.model.Notification
import com.example.final_project.networking.NotificationsService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NotificationsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        NotificationsService.instance.getData(root, ::setRecyclerView)

        return root
    }

    /**
     * Sets the recycler view of the notifications list
     */
    private suspend fun setRecyclerView(view: View, response: String) {
        // Parse the response
        val notifications = Gson().fromJson(response, Array<Notification>::class.java).toList()

        withContext(Dispatchers.Main) {
            // Get optional web view to show in landscape
            var webView: WebView? = null
            if (view.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                webView = view.findViewById(R.id.notifications_web_view)
            }

            // Get the recycler view
            val recyclerView = view.findViewById<RecyclerView>(R.id.notificationRV)
            val layoutManager = LinearLayoutManager(view.context)
            val dividerItemDecoration =
                DividerItemDecoration(recyclerView.context, layoutManager.orientation)

            // Set the adapter
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = NotificationRecyclerAdapter(notifications, webView)
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }
}