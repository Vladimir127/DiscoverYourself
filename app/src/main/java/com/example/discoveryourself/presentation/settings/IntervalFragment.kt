package com.example.discoveryourself.presentation.settings

import android.view.View
import android.widget.NumberPicker
import com.example.discoveryourself.databinding.FragmentIntervalBinding
import com.example.discoveryourself.domain.models.Interval
import com.example.discoveryourself.utils.AlertUtils
import com.example.discoveryourself.utils.Constants.MIN_INTERVAL_LENGTH
import com.example.discoveryourself.utils.TimeUtils

class IntervalFragment :
    BaseSettingFragment<FragmentIntervalBinding>(FragmentIntervalBinding::inflate) {
    private val hoursArray = Array(24) { i -> i.toString().padStart(2, '0') }
    private val minutesArray = Array(60) { i -> i.toString().padStart(2, '0') }

    override fun initViews() {
        with(binding) {
            MenuImageView.setOnClickListener { setSave(it) }
            CanselTextView.setOnClickListener { goBack(it) }
            SaveTextView.setOnClickListener { saveAndGoBack(it) }

            initNumberPicker(HoursNumberPickerStart, hoursArray)
            initNumberPicker(MinutNumberPickerStart, minutesArray)

            initNumberPicker(HoursNumberPickerEnd, hoursArray)
            initNumberPicker(MinutNumberPickerEnd, minutesArray)

            settingsViewModel.intervalLiveData.observe(viewLifecycleOwner) { interval ->
                val hoursStart = interval.startTime.substring(0, 2).toInt()
                val minutesStart = interval.startTime.substring(2, 4).toInt()
                val hoursEnd = interval.endTime.substring(0, 2).toInt()
                val minutesEnd = interval.endTime.substring(2, 4).toInt()

                HoursNumberPickerStart.value = hoursStart
                MinutNumberPickerStart.value = minutesStart

                HoursNumberPickerEnd.value = hoursEnd
                MinutNumberPickerEnd.value = minutesEnd
            }
        }
    }

    private fun initNumberPicker(hoursNumberPicker: NumberPicker, valuesArray: Array<String>) {
        hoursNumberPicker.minValue = 0
        hoursNumberPicker.maxValue = valuesArray.size - 1
        hoursNumberPicker.displayedValues = valuesArray
        hoursNumberPicker.wrapSelectorWheel = true
    }

    override fun isChanged(): Boolean {
        with(binding) {
            val interval = settingsViewModel.intervalLiveData.value

            val startTimeInitial = interval?.startTime
            val startTimeFinal: String =
                calculateTime(HoursNumberPickerStart, MinutNumberPickerStart)

            val endTimeInitial = interval?.endTime
            val endTimeFinal: String = calculateTime(HoursNumberPickerEnd, MinutNumberPickerEnd)
            return startTimeInitial != startTimeFinal || endTimeInitial != endTimeFinal
        }
    }

    /**
     * Конструирует и возаращает строку времени, которая в дальнейшем будет сохранена
     */
    private fun calculateTime(hoursPicker: NumberPicker, minutesPicker: NumberPicker): String {
        val pos = hoursPicker.value
        val h = hoursArray[pos]
        val pos2 = minutesPicker.value
        val m = minutesArray[pos2]
        return h + m
    }

    /**
     * Проверяет интервал на корректность (начальное время должно быть меньше
     * конечного). Поскольку время представлено в виде строк формата
     * 0600, 0930, 1245, эти строки просто сравниваются с помощью метода compareTo()
     * @param interval интервал, который необходимо проверить
     * @return Истина, если начальное время меньше конечного, иначе - ложь.
     */
    private fun isCorrect(interval: Interval): Boolean {
        val startTime: String = interval.startTime
        val endTime: String = interval.endTime
        return startTime < endTime
    }

    /**
     * Проверяет, достигает ли главный интервал минимальной допустимой длины - 1 час.
     * @param interval интервал, который необходимо проверить
     * @return Истина, если продолжительность интервала больше или равна одному часу, иначе - ложь.
     */
    private fun isIntervalMoreOneHour(interval: Interval): Boolean {
        val totalLength: Int = TimeUtils.calculateLength(interval)
        return totalLength >= MIN_INTERVAL_LENGTH
    }

    override fun saveAndGoBack(view: View) {
        with(binding) {
            val startTime = calculateTime(HoursNumberPickerStart, MinutNumberPickerStart)
            val endTime = calculateTime(HoursNumberPickerEnd, MinutNumberPickerEnd)
            val interval = Interval(0, startTime, endTime, false)

            if (!isCorrect(interval)) {
                AlertUtils.showError(
                    requireContext(),
                    "Начальное время интервала должно быть меньше конечного"
                )
            } else if (!isIntervalMoreOneHour(interval)) {
                AlertUtils.showError(
                    requireContext(),
                    "Минимальный интервал составляет 1 час"
                )
            } else {
                settingsViewModel.updateInterval(interval)
                goBack(view)
            }
        }
    }
}