package com.example.discoveryourself.presentation.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.discoveryourself.domain.models.AlarmSettings
import com.example.discoveryourself.domain.models.Interval
import com.example.discoveryourself.domain.repository.AlarmRepository
import com.example.discoveryourself.infrastructure.MyApp
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var repository: AlarmRepository

    var isChanged: Boolean = false

    init {
        (application as MyApp).appComponent.inject(this)

        viewModelScope.launch {
            val alarmSettings = repository.getAlarmSettings()
            _alarmSettings.value = alarmSettings
            _countLiveData.value = alarmSettings.count
            _volumeLiveData.value = alarmSettings.volume
            _textLiveData.value = alarmSettings.text
            _typeLiveData.value = alarmSettings.type
            _intervalLiveData.value = alarmSettings.interval
            _excludedIntervalsLiveData.value = alarmSettings.excludedIntervals
        }
    }

    private val _alarmSettings: MutableLiveData<AlarmSettings> = MutableLiveData()
    val alarmSettings: LiveData<AlarmSettings>
        get() = _alarmSettings

    private val _countLiveData: MutableLiveData<Int> = MutableLiveData()
    val countLiveData: LiveData<Int>
        get() = _countLiveData

    private val _volumeLiveData: MutableLiveData<Int> = MutableLiveData()
    val volumeLiveData: LiveData<Int>
        get() = _volumeLiveData

    private val _textLiveData: MutableLiveData<String> = MutableLiveData()
    val textLiveData: LiveData<String>
        get() = _textLiveData

    private val _typeLiveData: MutableLiveData<String> = MutableLiveData()
    val typeLiveData: LiveData<String>
        get() = _typeLiveData

    private val _intervalLiveData: MutableLiveData<Interval> = MutableLiveData()
    val intervalLiveData: LiveData<Interval>
        get() = _intervalLiveData

    private val _excludedIntervalsLiveData: MutableLiveData<List<Interval>> = MutableLiveData()
    val excludedIntervalsLiveData: LiveData<List<Interval>>
        get() = _excludedIntervalsLiveData

    fun updateCount(value: Int) {
        _countLiveData.value = value
        _alarmSettings.value?.count = value
        isChanged = true
    }

    fun updateVolume(value: Int) {
        _volumeLiveData.value = value
        _alarmSettings.value?.volume = value
        isChanged = true
    }

    fun updateText(value: String) {
        _textLiveData.value = value
        _alarmSettings.value?.text = value
        isChanged = true
    }

    fun updateType(value: String) {
        _typeLiveData.value = value
        _alarmSettings.value?.type = value
        isChanged = true
    }

    fun updateInterval(value: Interval) {
        // Поскольку во фрагменте IntervalFragment при сохранении формируется новый интервал, у него
        // потеряется значение ID, а оно нам нужно, чтобы можно было обновить в базе уже существующую
        // запись, а не создавать новую. В исходном же интервале, хранящемся в объекте LiveData, этот
        // ID имеются, поэтому мы просто обновляем свойства исходного интервала значениями из обновлённого интервала.
        val currentInterval = _intervalLiveData.value
        currentInterval?.let {
            val updatedInterval = it.copy(
                startTime = value.startTime,
                endTime = value.endTime
            )
            _intervalLiveData.value = updatedInterval
            _alarmSettings.value?.interval = updatedInterval
            isChanged = true
        }
    }

    fun updateExcludedIntervals(updatedIntervals: List<Interval>) {
        val currentExcludedIntervals = _excludedIntervalsLiveData.value.orEmpty().toMutableList()

        // Поскольку во фрагменте ExcludedIntervalsFragment при сохранении формируется новая коллекция
        // интервалов, у неё потеряются значения ID, а они нам нужны, чтобы можно было обновить в базе
        // уже существующие записи, а не создавать новые. В исходной же коллекции, хранящейся в объекте
        // LiveData, эти ID имеются, поэтому мы просто перебираем две эти коллекции и у каждого элемента
        // исходной коллекции обновляем свойства значениями из соответствующего элемента обновлённой
        // коллекции. Соответствия при этом устанавливаются по индексу.
        for (i in updatedIntervals.indices) {
            if (i < currentExcludedIntervals.size) {
                val updatedInterval = updatedIntervals[i]
                val currentInterval = currentExcludedIntervals[i]

                val updated = currentInterval.copy(
                    startTime = updatedInterval.startTime,
                    endTime = updatedInterval.endTime,
                    isEnabled = updatedInterval.isEnabled
                )
                currentExcludedIntervals[i] = updated
            }
        }

        _excludedIntervalsLiveData.value = currentExcludedIntervals
        _alarmSettings.value?.excludedIntervals = currentExcludedIntervals
        isChanged = true
    }

    fun saveSettings() {
        viewModelScope.launch {
            _alarmSettings.value?.let { repository.saveAlarmSettings(it) }
            isChanged = false
        }
    }

    // Метод для сохранения настроек
//    fun saveSettings(alarmSettings: AlarmSettingsEntity) {
//        viewModelScope.launch {
//            repository.saveAlarmSettings(alarmSettings)
//        }
//    }

    /*class Factory(private val repository: RoomAlarmRepositoryImpl) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }*/
}