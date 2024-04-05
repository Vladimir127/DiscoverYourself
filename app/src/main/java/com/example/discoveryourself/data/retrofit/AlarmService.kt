package com.example.discoveryourself.data.retrofit

import com.example.discoveryourself.data.retrofit.models.ReminderResponse
import retrofit2.http.GET

interface AlarmService {
    @GET("contents/reminder")
    fun getReposForUser(): ReminderResponse
}