package com.gallery.image_utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gallery.R
import com.squareup.picasso.Picasso



class ImageDetailsFragment : Fragment() {
    private lateinit var imageView: ImageView
    private lateinit var descriptionTextView: TextView
    private lateinit var ratingBar: RatingBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.image_details_fragment, container, false)

        this.imageView = view.findViewById(R.id.image_view_fragment)
        this.descriptionTextView = view.findViewById(R.id.description_text_view_fragment)
        this.ratingBar = view.findViewById(R.id.rating_bar_fragment)

        val id = ImageSet.fragment

        Picasso.get()
            .load(ImageSet.url(id))
            .resize(0, 600)
            .into(this.imageView)

        this.descriptionTextView.text = ImageSet.description(id)

        this.ratingBar.rating = ImageSet.rating(id)
        this.ratingBar.setOnRatingBarChangeListener { _, newRating, _ ->
            ImageSet.setRating(id, newRating)
        }

        return view
    }

}