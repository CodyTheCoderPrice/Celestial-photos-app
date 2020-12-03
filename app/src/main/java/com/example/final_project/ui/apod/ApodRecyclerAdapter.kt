package com.example.final_project.ui.apod

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.model.FavoriteApod

class ApodRecyclerAdapter(val favoriteApods: List<FavoriteApod>) : RecyclerView.Adapter<ApodRecyclerAdapter.ApodHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ApodHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.apod_list_view, parent, false)
        return ApodHolder(view)
    }

    override fun getItemCount(): Int {
        return favoriteApods.size
    }

    override fun onBindViewHolder(
        holder: ApodHolder,
        position: Int
    ) {
        val apod = favoriteApods[position]
        holder.title.text = apod.title
        holder.url = apod.url
    }

    class ApodHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var url = ""
        var title = v.findViewById<TextView>(R.id.all_apods_title)

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            ContextCompat.startActivity(v.context, Intent(Intent.ACTION_VIEW, Uri.parse(url)), null)
        }
    }
}