package com.example.discoveryourself.domain.repository

import com.example.discoveryourself.data.retrofit.models.ReminderResponse

interface AlarmMediaRepository {
    suspend fun getReminders(): ReminderResponse
}