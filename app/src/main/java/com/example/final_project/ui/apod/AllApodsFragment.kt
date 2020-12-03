package com.example.final_project.ui.apod

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.database.FavoriteApodRepository
import com.example.final_project.model.FavoriteApodModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllApodsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_all_apods, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.apodRV)
        val layoutManager = LinearLayoutManager(root.context)

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch(Dispatchers.Default) {
            val favoriteApodModel = FavoriteApodModel(FavoriteApodRepository(root.context))

            // Set the adapter
            if (recyclerView != null) {
                Log.d("Here", "${favoriteApodModel.getFavoriteApods().size}")

                val dividerItemDecoration =
                    DividerItemDecoration(recyclerView?.context, layoutManager.orientation)

                withContext(Dispatchers.Main) {
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = ApodRecyclerAdapter(favoriteApodModel.getFavoriteApods())
                    recyclerView.addItemDecoration(dividerItemDecoration)
                }
            }
        }

        return root
    }

    companion object {
        fun newInstance() = AllApodsFragment()
    }
}