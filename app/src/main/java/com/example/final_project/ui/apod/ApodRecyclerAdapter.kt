package com.example.final_project.ui.apod

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.model.FavoriteApod

class ApodRecyclerAdapter(val favoriteApods: List<FavoriteApod>, val webView: WebView?) : RecyclerView.Adapter<ApodRecyclerAdapter.ApodHolder>() {
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

        // Set callback for the recycler view holder
        when (webView) {
            null -> {
                Log.d("Here","Was null")
                // Open url in chrome
                holder.callback = {
                    ContextCompat.startActivity(
                        it.context,
                        Intent(Intent.ACTION_VIEW, Uri.parse(apod.url)),
                        null
                    )
                }
            }
            else -> {
                // Open url in web view
                holder.callback = {
                    webView.loadUrl(apod.url)
                }
            }
        }
    }

    class ApodHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var url = ""
        var title = v.findViewById<TextView>(R.id.all_apods_title)
        lateinit var callback: (v: View) -> Unit

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            callback(v)
            //ContextCompat.startActivity(v.context, Intent(Intent.ACTION_VIEW, Uri.parse(url)), null)
        }
    }
}