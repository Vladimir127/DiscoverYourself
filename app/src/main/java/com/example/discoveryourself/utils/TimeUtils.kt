package com.example.discoveryourself.utils

import com.example.discoveryourself.domain.models.Interval

object TimeUtils {
    /**
     * Преобразует строку времени формата 0630 в целое число минут вида 390
     *
     * @param timeString Строка времени вида 0600
     * @return Количество минут в этом времени, например, 390
     */
    private fun timeStringToMinutes(timeString: String): Int {
        val hours = timeString.substring(0, 2).toInt()
        val minutes = timeString.substring(2, 4).toInt()
        return hours * 60 + minutes
    }

    /**
     * Вычисляет продолжительность интервала в минутах, вычитая из конечного времени начальное
     * @param interval Интервал, продолжительность которого необходимо вычислить
     * @return Продолжительность интервала в минутах
     */
    fun calculateLength(interval: Interval): Int {
        val startTimeString: String = interval.startTime
        val endTimeString: String = interval.endTime
        val startTimeMinutes: Int =
            timeStringToMinutes(startTimeString)
        val endTimeMinutes: Int =
            timeStringToMinutes(endTimeString)
        return endTimeMinutes - startTimeMinutes
    }

    /**
     * Преобразует строку вида 0600, полученную из объекта SignalInfo,
     * в строку времени вида 06:00 для отображения в макете
     *
     * @param initialString Строка вида 0600
     * @return Строка вида 06:00
     */
    fun createTimeString(initialString: String): String {
        val hours = initialString.substring(0, 2)
        val minutes = initialString.substring(2, 4)
        return "$hours:$minutes"
    }
}