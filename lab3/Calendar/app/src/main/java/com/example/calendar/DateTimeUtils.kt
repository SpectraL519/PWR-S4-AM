package com.example.calendar

import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class DateTimeUtils {
    companion object {
        val format: Map<String, String> = mapOf(
            "month_year" to "MMM yyyy",
            "full" to "dd-MM-yyyy"
        )


        fun getDateFormat(date: LocalDate, format: String, addMonth: Boolean = false): String {
            val formatter = DateTimeFormatter.ofPattern(format)

            if (addMonth) {
                val correctedDate = date.plusMonths(1)
                return correctedDate.format(formatter)
            }
            return date.format(formatter)
        }

        fun getTimeFormat(time: LocalTime): String {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return time.format(formatter)
        }

        fun getMonthDays(date: LocalDate): ArrayList<LocalDate?> {
            val maxDays = 42
            val monthDays = ArrayList<LocalDate?>()

            val numDays: Int = YearMonth.from(date).lengthOfMonth()
            val firstDay: LocalDate = date.withDayOfMonth(1)
            val dayOfWeek: Int = firstDay.dayOfWeek.value
            var firstDayIndex: Int = 0

            for (i in 1 until maxDays + 1) {
                if (i < dayOfWeek || i >= numDays + dayOfWeek) {
                    monthDays.add(null)
                }
                else {
                    if (i == dayOfWeek)
                        firstDayIndex = i
                    monthDays.add(firstDay.plusDays((i - firstDayIndex).toLong()))
                }
            }

            return monthDays
        }
    }
}