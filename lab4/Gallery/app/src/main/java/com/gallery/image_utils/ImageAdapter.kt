package com.gallery.image_utils

import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.gallery.MainActivity
import com.gallery.R
import com.squareup.picasso.Picasso



class ImageAdapter
    constructor(
        private val urlList: ArrayList<String>,
        private val context: MainActivity
    ) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setOnClickListener {
            val orientation: Int = this.context.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                val intent = Intent(this.context, ImageDetailsActivity::class.java)
                intent.putExtra("image_id", position)
                context.startActivity(intent)
            }
            else {
                ImageSet.fragment = position
                context.refreshFragment()
            }
        }

        Picasso.get()
            .load(this.urlList[position])
            .resize(0, 600)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = this.urlList.size


    inner class ViewHolder (itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById<ImageView>(R.id.small_image_view)
    }
}