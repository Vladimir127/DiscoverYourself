package com.example.discoveryourself.domain.models

data class AlarmSettings(
    val id: Int,
    var count: Int,
    val volume: Int,
    val text: String,
    var changed: Boolean
)