package com.gallery.image_utils

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gallery.R
import com.squareup.picasso.Picasso



class ImageDetailsActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var descriptionTextView: TextView
    private lateinit var ratingBar: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_details_activity)

        val id = intent.getIntExtra("image_id", 0)

        this.imageView = findViewById(R.id.image_view)
        this.descriptionTextView = findViewById(R.id.description_text_view)
        this.ratingBar = findViewById(R.id.rating_bar)

        Picasso.get()
            .load(ImageSet.url(id))
            .into(this.imageView)

        this.descriptionTextView.text = ImageSet.description(id)

        this.ratingBar.rating = ImageSet.rating(id)
        this.ratingBar.setOnRatingBarChangeListener { _, newRating, _ ->
            ImageSet.setRating(id, newRating)
        }
    }
}