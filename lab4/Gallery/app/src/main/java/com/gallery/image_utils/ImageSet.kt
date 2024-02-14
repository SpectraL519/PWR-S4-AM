package com.gallery.image_utils



object ImageSet {
    var fragment: Int = 0
    private var images = ArrayList<Image>()

    fun add(image: Image) {
        this.images.add(image)
    }

    fun initialized(): Boolean = (this.images.size > 0)
    fun urlList(): ArrayList<String> = this.images.mapTo(ArrayList<String>()) { it.url }
    fun url (id: Int): String = this.images[id].url
    fun description (id: Int): String = this.images[id].description
    fun rating (id: Int): Float = this.images[id].rating

    fun setRating (id: Int, newRating: Float) {
        this.images[id].rating = newRating
    }

    fun sortByRating() {
        this.images = ArrayList(this.images.sortedByDescending { it.rating })
    }
}