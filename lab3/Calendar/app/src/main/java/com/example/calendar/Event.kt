package com.example.calendar

import java.time.LocalDate
import java.time.LocalTime



class Event
    constructor(private val name: String,
                private val date: LocalDate,
                private val allDay: Boolean,
                private val start: LocalTime?,
                private val end: LocalTime?,
                private val notes: String?) {

    fun getDate(): LocalDate = this.date

    fun getOverview(): String {
        if (this.allDay)
            return "${this.name} -> all day"
        return "${this.name} -> starts at ${DateTimeUtils.getTimeFormat(this.start!!)}"
    }

    fun getDescription(): String {
        if (this.allDay)
            return """
            EVENT: ${this.name}
            
            DATE: ${DateTimeUtils.getDateFormat(this.date, DateTimeUtils.format["full"]!!)}
            All day
            
            NOTES: 
            ${this.notes}
            """.trimIndent()

        return """
        EVENT: ${this.name}
        
        DATE: ${DateTimeUtils.getDateFormat(this.date, DateTimeUtils.format["full"]!!)}
        FROM: ${DateTimeUtils.getTimeFormat(this.start!!)}
        TO: ${DateTimeUtils.getTimeFormat(this.end!!)}
        
        NOTES: 
        ${this.notes}
        """.trimIndent()
    }
}