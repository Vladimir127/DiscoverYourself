package com.example.discoveryourself.domain.models

data class Interval(
    val id: Long = 0,
    val startTime: String,
    val endTime: String,
    val isEnabled: Boolean
) {
    override fun toString(): String {
        val startTimeString: String = createTimeString(startTime)
        val endTimeString: String = createTimeString(endTime)

        return "$startTimeString - $endTimeString"
    }

    /**
     * Преобразует строку вида 0600, полученную из объекта AlarmSettings,
     * в строку времени вида 06:00 для отображения в макете
     * @param initialString Строка вида 0600
     * @return Строка вида 06:00
     */
    private fun createTimeString(initialString: String): String {
        val hours = initialString.substring(0, 2)
        val minutes = initialString.substring(2, 4)
        return "$hours:$minutes"
    }
}