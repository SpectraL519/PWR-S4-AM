package com.example.calendar

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.collections.ArrayList


class MainActivity :
    AppCompatActivity(), CalendarAdapter.CellListener, EventAdapter.EventListener {
    private var monthYearLabel: TextView? = null
    private var calendarView: RecyclerView? = null
    private var calendarAdapter: CalendarAdapter? = null
    private var eventsView: RecyclerView? = null
    private var eventAdapter: EventAdapter? = null

    private var currentDate: LocalDate = LocalDate.now()
    private var contextDate: LocalDate? = null
    private var selectedDate: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainActivity.events = HashMap()

        this.initView()
        this.contextDate = LocalDate.now()
        this.setMonthView()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putString("currentDate", DateTimeUtils.getDateFormat(this.currentDate, DateTimeUtils.format["full"]!!))
        outState.putString("contextDate", DateTimeUtils.getDateFormat(this.contextDate!!, DateTimeUtils.format["full"]!!))
        outState.putString("selectedDate", this.selectedDate)
        outState.putSerializable("events", MainActivity.events)

        super.onSaveInstanceState(outState, outPersistentState)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        this.currentDate = LocalDate.parse(savedInstanceState?.getString("currentDate"),
                                           DateTimeFormatter.ofPattern(DateTimeUtils.format["full"]))
        this.contextDate = LocalDate.parse(savedInstanceState?.getString("contextDate"),
                                           DateTimeFormatter.ofPattern(DateTimeUtils.format["full"]))
        this.selectedDate = savedInstanceState?.getString("selectedDate")
        MainActivity.events = savedInstanceState?.getSerializable("events", HashMap::class.java) as HashMap<String, ArrayList<Event>>

        super.onRestoreInstanceState(savedInstanceState, persistentState)

        this.setMonthView()
        this.clearCalendarView()
        this.setEventsView()
    }

    private fun initView() {
        this.monthYearLabel = findViewById<TextView>(R.id.month_year_label)

        this.calendarView = findViewById<RecyclerView>(R.id.calendar_recycler_view)
        this.calendarView?.setHasFixedSize(true)
        this.calendarAdapter = CalendarAdapter(this)
        this.calendarView?.layoutManager = GridLayoutManager(this, 7)
        this.calendarView?.adapter = this.calendarAdapter
        this.clearCalendarView()

        this.eventsView = findViewById<RecyclerView>(R.id.events_recycler_view)
        this.eventAdapter = EventAdapter(this)
        this.eventsView?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        this.eventsView?.adapter = this.eventAdapter
    }

    private fun setMonthView() {
        this.monthYearLabel?.text =
            DateTimeUtils.getDateFormat(this.contextDate!!, DateTimeUtils.format["month_year"]!!)
        this.calendarAdapter?.setMonthDays(DateTimeUtils.getMonthDays(this.contextDate!!))
    }

    private fun setEventsView() {
        this.eventAdapter?.setEvents(MainActivity.events[this.selectedDate])
    }

    // Button click handlers
    fun previousMonth(view: View) {
        this.contextDate = this.contextDate?.minusMonths(1)
        this.selectedDate = null
        this.setMonthView()
    }

    fun nextMonth(view: View) {
        this.contextDate = this.contextDate?.plusMonths(1)
        this.selectedDate = null
        this.setMonthView()
    }

    private fun clearCalendarView() {
        for (i: Int in 0 until this.calendarView?.adapter?.itemCount!!) {
            val viewHolder: CalendarCellViewHolder =
                this.calendarView?.findViewHolderForAdapterPosition(i) as CalendarCellViewHolder
            viewHolder.getCellView().setBackgroundResource(0)
            val date: LocalDate? = viewHolder.getDate()
            if (date != null && date.compareTo(this.currentDate) == 0) {
                viewHolder.getCellView().setBackgroundResource(R.drawable.selected)
            }
        }
    }

    fun newEvent(view: View) {
        startActivity(
            Intent(this, NewEventActivity::class.java).apply {
                putExtra("default_date", selectedDate)
            }
        )
        this.setEventsView()
    }


    // Cell click handler
    override fun cellOnClick(position: Int, date: LocalDate?, cellView: TextView) {
        if (date != null) {
            this.selectedDate = DateTimeUtils.getDateFormat(date, DateTimeUtils.format["full"]!!)

            // Visual selection
            this.clearCalendarView()
                cellView.setBackgroundResource(R.drawable.selected_full)
            this.setEventsView()
        }
    }

    override fun eventCardOnClick(position: Int, event: Event) {
        val eventDialog = Dialog(this)
        eventDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        eventDialog.setCancelable(true)
        eventDialog.setContentView(R.layout.event_dialog)

        val eventDescription: TextView = eventDialog.findViewById<TextView>(R.id.event_description)
        eventDescription.text = event.getDescription()

        val eventOkButton: Button = eventDialog.findViewById<Button>(R.id.event_ok_button)
        eventOkButton.setOnClickListener( View.OnClickListener {
            eventDialog.dismiss()
        })

        val eventDeleteButton: Button = eventDialog.findViewById<Button>(R.id.event_delete_button)
        eventDeleteButton.setOnClickListener( View.OnClickListener {
            val date: String = DateTimeUtils.getDateFormat(event.getDate(), DateTimeUtils.format["full"]!!)
            if (events.containsKey(date)) {
                events[date]!!.remove(event)
            }
            eventDialog.dismiss()
            setEventsView()
        })

        eventDialog.show()
    }

    companion object {
        private lateinit var events: HashMap<String, ArrayList<Event>>
        fun addEvent(event: Event) {
            val date: String = DateTimeUtils.getDateFormat(event.getDate(), DateTimeUtils.format["full"]!!)
            if (this.events.containsKey(date))
                this.events[date]?.add(event)
            else
                this.events[date] = arrayListOf<Event>(event)
        }
    }
}