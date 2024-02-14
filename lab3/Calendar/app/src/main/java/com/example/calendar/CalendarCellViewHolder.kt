package com.example.calendar

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate


class CalendarCellViewHolder
    constructor(itemView: View, private val cellListener: CalendarAdapter.CellListener)
    : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var cellView: TextView = itemView.findViewById<TextView>(R.id.cell_item)
    private var date: LocalDate? = null

    init {
        this.cellView.setOnClickListener(this)
        this.cellView.gravity = Gravity.CENTER
    }

    fun getCellView(): TextView = this.cellView
    fun getDate(): LocalDate? = this.date

    fun bind(date: LocalDate?) {
        this.date = date
        if (this.date == null)
            this.cellView.text = ""
        else
            this.cellView.text = this.date!!.dayOfMonth.toString()
    }

    override fun onClick(view: View?) {
        this.cellListener.cellOnClick(adapterPosition, this.date, view as TextView)
    }
}