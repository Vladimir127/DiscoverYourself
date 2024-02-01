package com.example.discoveryourself.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.discoveryourself.R
import com.example.discoveryourself.domain.models.AlarmSettings
import com.example.discoveryourself.infrastructure.MyApp
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private val repository by lazy { (application as MyApp).repository }

    val alarmSettings by lazy { repository.getAlarmSettings() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    //fun getAlarmSettings() : AlarmSettings = alarmSettings

    fun saveAlarmSettings() {
         repository.saveAlarmSettings(alarmSettings)
    }
}