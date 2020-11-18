package com.example.final_project.ui.apod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.database.ApodRepository
import com.example.final_project.model.ApodModel
import com.example.final_project.model.Notification
import com.example.final_project.ui.notifications.NotificationRecyclerAdapter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AllApodsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_all_apods, container, false)

        val apodModel: ApodModel = ApodModel(ApodRepository(root.context))

        val recyclerView = view?.findViewById<RecyclerView>(R.id.apodRV)
        val layoutManager = LinearLayoutManager(view?.context)
        val dividerItemDecoration =
            DividerItemDecoration(recyclerView?.context, layoutManager.orientation)

        // Set the adapter
        if (recyclerView != null) {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = ApodRecyclerAdapter(apodModel.getApods())
            recyclerView.addItemDecoration(dividerItemDecoration)
        }

        return root
    }

    private suspend fun setRecyclerView(view: View, response: String) {
        // Parse the response
        val notifications = Gson().fromJson(response, Array<Notification>::class.java).toList()

        withContext(Dispatchers.Main) {
            // Get the recycler view
            val recyclerView = view.findViewById<RecyclerView>(R.id.notificationRV)
            val layoutManager = LinearLayoutManager(view.context)
            val dividerItemDecoration =
                DividerItemDecoration(recyclerView.context, layoutManager.orientation)

            // Set the adapter
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = NotificationRecyclerAdapter(notifications)
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }

    companion object {
        fun newInstance() = AllApodsFragment()
    }
}