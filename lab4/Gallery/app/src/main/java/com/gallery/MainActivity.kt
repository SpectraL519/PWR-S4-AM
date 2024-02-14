package com.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gallery.image_utils.Image
import com.gallery.image_utils.ImageAdapter
import com.gallery.image_utils.ImageComponentAdapter
import com.gallery.image_utils.ImageSet
import com.squareup.picasso.Picasso



class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!ImageSet.initialized()) {
            val imageUrls: Array<String> = resources.getStringArray(R.array.image_urls)
            for ((index, url) in imageUrls.withIndex()) {
                ImageSet.add(Image(url, String.format("description_%d", index), 0.0f))
            }
        }

        recyclerView = findViewById(R.id.recycler_view)
    }

    override fun onResume() {
        super.onResume()

        ImageSet.sortByRating()
        recyclerView.adapter = ImageComponentAdapter(ImageSet.urlList(), this)
    }

    fun refreshFragment() {
        val imageView: ImageView = findViewById(R.id.image_view_fragment)
        val descriptionTextView: TextView = findViewById(R.id.description_text_view_fragment)
        val ratingBar: RatingBar = findViewById(R.id.rating_bar_fragment)

        val id = ImageSet.fragment

        Picasso.get()
            .load(ImageSet.url(id))
            .into(imageView)

        descriptionTextView.text = ImageSet.description(id)

        ratingBar.rating = ImageSet.rating(id)
        ratingBar.setOnRatingBarChangeListener { _, newRating, _ ->
            ImageSet.setRating(id, newRating)
            onResume()
        }
    }
}