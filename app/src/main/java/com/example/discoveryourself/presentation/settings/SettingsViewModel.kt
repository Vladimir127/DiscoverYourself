package com.example.discoveryourself.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.discoveryourself.data.repositories.RoomAlarmRepositoryImpl

class SettingsViewModel(private val repository: RoomAlarmRepositoryImpl) : ViewModel() {
    var changed: Boolean = false

    // Свойства для хранения текущих настроек
    /*var count: MutableLiveData<Int> = MutableLiveData()
    var volume: MutableLiveData<Int> = MutableLiveData()
    var text: MutableLiveData<String> = MutableLiveData()*/

    /*val alarmSettings = repository.getAlarmSettings().map { settings ->
        settings ?: AlarmSettingsEntity(
            id = 1,
            count = 3, // Значение по умолчанию для count
            volume = 50, // Значение по умолчанию для volume
            text = ""
        )
    }*/

    /*fun saveSettings() {
        viewModelScope.launch {
            val alarmSettings = AlarmSettings(
                id = 1,
                count = count.value ?: 0,
                volume = volume.value ?: 0,
                text = text.value.orEmpty()
            )
            repository.saveAlarmSettings(alarmSettings)
        }
    }*/

    // Метод для сохранения настроек
    /*fun saveSettings(alarmSettings: AlarmSettingsEntity) {
        viewModelScope.launch {
            repository.saveAlarmSettings(alarmSettings)
        }
    }*/

    class Factory(private val repository: RoomAlarmRepositoryImpl) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}