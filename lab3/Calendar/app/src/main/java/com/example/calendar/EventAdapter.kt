package com.example.calendar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class EventAdapter
    constructor(private val eventListener: EventListener)
    : RecyclerView.Adapter<EventViewHolder>() {

    private var events: ArrayList<Event>? = ArrayList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.event_card, parent, false)
        return EventViewHolder(itemView, this.eventListener)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        if (this.events != null)
            holder.bind(this.events!![position])
    }

    override fun getItemCount(): Int {
        if (this.events == null)
            return 0
        return this.events!!.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setEvents(events: ArrayList<Event>?) {
        this.events = events
        notifyDataSetChanged()
    }

    interface EventListener {
        fun eventCardOnClick(position: Int, event: Event)
    }
}