package com.example.androidassignment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidassignment.R
import com.example.androidassignment.models.jsonFeedRow
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_listitem.view.*

/**
 * @author girishsharma
 * this is a adapter class used to used to inflate view in the RecyclerView
 */
class FeedsAdapter(private val jsonFeedRows: List<jsonFeedRow>?, private val context: Context) : RecyclerView.Adapter<FeedsAdapter.FeedView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedView {
        val inflater = LayoutInflater.from(parent.context)
        val layoutView = inflater.inflate(R.layout.layout_listitem, parent, false)
        return FeedView(layoutView)
    }

    override fun onBindViewHolder(holder: FeedView, position: Int) {
        val row = jsonFeedRows!![position]
        if (row.title != null) {
            holder.titleText.text = row.title
        } else {
            holder.titleText.text = context.resources.getString(R.string.textNA)
        }
        if (row.description != null) {
            holder.descriptionText.text = row.description
        } else {
            holder.descriptionText.text = context.resources.getString(R.string.textNA)
        }

        /**
         * Using third party library for image Lazy loading
         * */
        Picasso.get().load(row.imageHref).placeholder(R.mipmap.ic_launcher_round)
                .networkPolicy(NetworkPolicy.NO_CACHE).into(holder.imageView);
    }

    override fun getItemCount(): Int {
        return jsonFeedRows?.size ?: 0
    }

    /**
     * View to represent single row item
     * */
    inner class FeedView(itemView: View) : ViewHolder(itemView) {
        val titleText: TextView = itemView.txtViewHeader
        val descriptionText: TextView = itemView.txtViewDesc
        val imageView: ImageView = itemView.imgViewImage
    }
}