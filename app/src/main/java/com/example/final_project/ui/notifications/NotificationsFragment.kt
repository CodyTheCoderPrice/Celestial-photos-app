package com.example.final_project.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.model.Notification


class NotificationsFragment : Fragment() {

    private lateinit var homeViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        val list = listOf(
            Notification("A", "1", "https://google.com", "12345", "Hello World"),
            Notification("AS", "1", "https://google.com", "12345", "Hello World"),
            Notification("ASD", "1", "https://google.com", "12345", "Hello World"),
            Notification("ASDF", "1", "https://google.com", "12345", "Hello World")
        )

        val recyclerView = root.findViewById<RecyclerView>(R.id.notificationRV)
        val layoutManager =  LinearLayoutManager(root.context)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = NotificationRecyclerAdapter(list)
        recyclerView.addItemDecoration(dividerItemDecoration)
        return root
    }
}