package com.example.calendar

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate


class EventViewHolder
    constructor(itemView: View, private val eventListener: EventAdapter.EventListener)
    : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var eventCardView: CardView = itemView.findViewById<CardView>(R.id.event_card_view)
    private var eventTextView: TextView = itemView.findViewById<TextView>(R.id.event_card_text)
    private var event: Event? = null

    init {
        this.eventCardView.setOnClickListener(this)
    }

    fun bind(event: Event) {
        this.event = event
        this.eventTextView.text = this.event!!.getOverview()
    }

    override fun onClick(view: View?) {
        this.eventListener.eventCardOnClick(adapterPosition, this.event!!)
    }
}