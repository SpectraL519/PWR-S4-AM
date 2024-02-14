package com.example.calendar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*



@Suppress("DEPRECATION")
@SuppressLint("UseSwitchCompatOrMaterialCode")
class NewEventActivity : AppCompatActivity() {
    private var eventTitle: EditText? = null
    private var eventDatePicker: TextInputLayout? = null
    private var eventAllDay: Switch? = null
    private var eventStartTime: TextInputLayout? = null
    private var eventEndTime: TextInputLayout? = null
    private var eventNotes: EditText? = null

    private var defaultDate: String? = null
    private var selectedDate: LocalDate? = null
    private var allDay: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_event_activity)

        this.initExtras()
        this.initView()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putString("title", this.eventTitle?.text.toString())
        outState.putString("date", this.eventDatePicker?.editText?.text.toString())
        outState.putString("start", this.eventStartTime?.editText?.text.toString())
        outState.putString("end", this.eventEndTime?.editText?.text.toString())
        outState.putString("notes", this.eventNotes?.text.toString())
        outState.putBoolean("allDay", this.allDay)

        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        this.eventTitle?.setText(savedInstanceState?.getString("title"))
        this.eventDatePicker?.editText?.setText(savedInstanceState?.getString("date"))
        this.eventStartTime?.editText?.setText(savedInstanceState?.getString("start"))
        this.eventEndTime?.editText?.setText(savedInstanceState?.getString("end"))
        this.eventNotes?.setText(savedInstanceState?.getString("notes"))
        this.allDay = savedInstanceState?.getBoolean("allDay")!!
        this.eventAllDay?.isChecked = this.allDay

        super.onRestoreInstanceState(savedInstanceState, persistentState)
    }

    private fun initExtras() {
        this.defaultDate = intent.getStringExtra("default_date")
        if (this.defaultDate != null)
            this.selectedDate = LocalDate.parse(
                this.defaultDate,
                DateTimeFormatter.ofPattern(DateTimeUtils.format["full"]!!)
            )
    }

    private fun initView() {
        this.eventTitle = findViewById<EditText>(R.id.title)

        this.eventDatePicker = findViewById<TextInputLayout>(R.id.date_picker)
        this.eventDatePicker?.editText?.setText(this.defaultDate)
        this.eventDatePicker?.setStartIconOnClickListener( View.OnClickListener {
            val c = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, day ->
                    this.selectedDate = LocalDate.of(year, month, day)
                    this.eventDatePicker?.editText?.setText(
                        DateTimeUtils.getDateFormat(this.selectedDate!!, DateTimeUtils.format["full"]!!, true)
                    )
                },
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        })

        this.eventAllDay = findViewById<Switch>(R.id.all_day)
        this.eventAllDay?.setOnCheckedChangeListener { _, isChecked -> this.allDay = isChecked }

        this.eventStartTime = findViewById<TextInputLayout>(R.id.start_time_picker)
        this.eventStartTime?.setStartIconOnClickListener( View.OnClickListener {
            val c = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                this,
                { _, hour, minute ->
                    this.eventStartTime?.editText?.setText(DateTimeUtils.getTimeFormat(LocalTime.of(hour, minute)))
                },
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true
            )
            timePickerDialog.show()
        })

        this.eventEndTime = findViewById<TextInputLayout>(R.id.end_time_picker)
        this.eventEndTime?.setStartIconOnClickListener( View.OnClickListener {
            val c = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                this,
                { _, hour, minute ->
                    this.eventEndTime?.editText?.setText(DateTimeUtils.getTimeFormat(LocalTime.of(hour, minute)))
                },
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true
            )
            timePickerDialog.show()
        })

        this.eventNotes = findViewById<EditText>(R.id.notes)
    }

    fun saveEvent(view: View) {
        val title: String = this.eventTitle?.text.toString()
        val start: String = this.eventStartTime?.editText?.text.toString()
        var startTime: LocalTime? = null
        val end: String = this.eventEndTime?.editText?.text.toString()
        var endTime: LocalTime? = null
        val notes: String = this.eventNotes?.text.toString()

        if (title == "") {
            Toast.makeText(this, "Empty event title!", Toast.LENGTH_SHORT).show()
            return
        }

        if (this.selectedDate == null) {
            Toast.makeText(this, "Date not selected!", Toast.LENGTH_SHORT).show()
            return
        }

        if (!this.allDay) {
            if (start == "") {
                Toast.makeText(this, "Start time not selected!", Toast.LENGTH_SHORT).show()
                return
            }
            if (end == "") {
                Toast.makeText(this, "End time not selected!", Toast.LENGTH_SHORT).show()
                return
            }

            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm")
            startTime = LocalTime.parse(start)
            endTime = LocalTime.parse(end)
            if (endTime < startTime) {
                Toast.makeText(this, "Event cannot end before it starts!", Toast.LENGTH_SHORT).show()
                return
            }
        }

        MainActivity.addEvent(Event(title, this.selectedDate!!, this.allDay, startTime, endTime, notes))
        Toast.makeText(this, "Event saved!", Toast.LENGTH_SHORT).show()
        finish()
    }
}