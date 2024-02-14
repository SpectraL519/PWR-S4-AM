package com.example.calendar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate


class CalendarAdapter
    constructor (private val cellListener: CellListener)
    : RecyclerView.Adapter<CalendarCellViewHolder>() {

    private var monthDays: ArrayList<LocalDate?> = ArrayList<LocalDate?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarCellViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell, parent, false)
        val layoutParams: ViewGroup.LayoutParams = itemView.layoutParams
        layoutParams.height = (parent.height / 6).toInt()
        return CalendarCellViewHolder(itemView, this.cellListener)
    }

    override fun onBindViewHolder(holder: CalendarCellViewHolder, position: Int) {
        holder.bind(this.monthDays[position])
    }

    override fun getItemCount(): Int = this.monthDays.size

    @SuppressLint("NotifyDataSetChanged")
    fun setMonthDays(monthDays: ArrayList<LocalDate?>) {
        this.monthDays = monthDays
        notifyDataSetChanged()
    }

    interface CellListener {
        fun cellOnClick(position: Int, date: LocalDate?, cellView: TextView)
    }
}