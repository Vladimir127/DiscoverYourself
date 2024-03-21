package com.example.discoveryourself.domain.models

data class AlarmSettings(
    val id: Long,
    var count: Int,
    var volume: Int,
    var text: String,
    var type: String,
    var interval: Interval,
    var excludedIntervals: List<Interval> = listOf(
        Interval(startTime = "1000", endTime = "1100", isEnabled = false),
        Interval(startTime = "1200", endTime = "1300", isEnabled = false),
        Interval(startTime = "1400", endTime = "1500", isEnabled = false)
    )
)