package com.example.discoveryourself.presentation.settings

import android.content.res.ColorStateList
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.NumberPicker
import com.example.discoveryourself.R
import com.example.discoveryourself.databinding.FragmentExcludedIntervalsBinding
import com.example.discoveryourself.domain.models.Interval
import com.example.discoveryourself.utils.AlertUtils

class ExcludedIntervalsFragment :
    BaseSettingFragment<FragmentExcludedIntervalsBinding>(FragmentExcludedIntervalsBinding::inflate) {
    private val hoursArray = Array(24) { i -> i.toString().padStart(2, '0') }
    private val minutesArray = Array(12) { i -> (i * 5).toString().padStart(2, '0') }

    override fun initViews() {
        with(binding) {
            menuImageView.setOnClickListener { setSave(it) }
            cancelTextView.setOnClickListener { goBack(it) }
            saveTextView.setOnClickListener { saveAndGoBack(it) }

            val brownColor = requireContext().getColor(R.color.radio_button_disabled)
            checkBox1.buttonTintList = ColorStateList.valueOf(brownColor)
            checkBox2.buttonTintList = ColorStateList.valueOf(brownColor)
            checkBox3.buttonTintList = ColorStateList.valueOf(brownColor)

            settingsViewModel.excludedIntervalsLiveData.observe(viewLifecycleOwner) { excludedIntervals ->
                if (excludedIntervals.size == 3) {
                    initializeInterval(
                        excludedIntervals[0],
                        hoursNumberPickerStart1,
                        minutesNumberPickerStart1,
                        hoursNumberPickerEnd1,
                        minutesNumberPickerEnd1,
                        checkBox1,
                        rel1,
                        rel2
                    )
                    initializeInterval(
                        excludedIntervals[1],
                        hoursNumberPickerStart2,
                        minutesNumberPickerStart2,
                        hoursNumberPickerEnd2,
                        minutesNumberPickerEnd2,
                        checkBox2,
                        rel3,
                        rel4
                    )
                    initializeInterval(
                        excludedIntervals[2],
                        hoursNumberPickerStart3,
                        minutesNumberPickerStart3,
                        hoursNumberPickerEnd3,
                        minutesNumberPickerEnd3,
                        checkBox3,
                        rel5,
                        rel6
                    )
                }
            }
        }
    }

    /**
     * Извлекает данные из интервала и устанавливает соответствующие значения
     * элементам управления на макете, отвечающим за отображение этого
     * интервала: элементу CheckBox, четырём элементам NumberPicker
     * @param interval интервал, значения из которого нужно отобразить
     * @param hoursNumberPickerStart барабан для выбора часов начального времени
     * @param minutesNumberPickerStart барабан для выбора минут начального времени
     * @param hoursNumberPickerEnd барабан для выбора часов конечного времени
     * @param minutesNumberPickerEnd барабан для выбора минут конечного времени
     * @param checkBox флажок для включения/отключения этого интервала
     * @param relStart RelativeLayout, содержащий начальное время
     * @param relEnd RelativeLayout, содержащий конечное время
     */
    private fun initializeInterval(
        interval: Interval,
        hoursNumberPickerStart: NumberPicker,
        minutesNumberPickerStart: NumberPicker,
        hoursNumberPickerEnd: NumberPicker,
        minutesNumberPickerEnd: NumberPicker,
        checkBox: CheckBox,
        relStart: LinearLayout,
        relEnd: LinearLayout
    ) {
        initNumberPicker(hoursNumberPickerStart, hoursArray)
        initNumberPicker(minutesNumberPickerStart, minutesArray)

        initNumberPicker(hoursNumberPickerEnd, hoursArray)
        initNumberPicker(minutesNumberPickerEnd, minutesArray)

        val hoursStart = interval.startTime.substring(0, 2).toInt()
        val minutesStart = interval.startTime.substring(2, 4).toInt()
        val hoursEnd = interval.endTime.substring(0, 2).toInt()
        val minutesEnd = interval.endTime.substring(2, 4).toInt()

        hoursNumberPickerStart.value = hoursStart
        minutesNumberPickerStart.value = minutesStart / 5

        hoursNumberPickerEnd.value = hoursEnd
        minutesNumberPickerEnd.value = minutesEnd / 5

        val isEnabled: Boolean = interval.isEnabled
        checkBox.isChecked = isEnabled
        //setRelativeLayoutEnabled(relStart, isEnabled);
        //setRelativeLayoutEnabled(relEnd, isEnabled);
    }

    private fun initNumberPicker(hoursNumberPicker: NumberPicker, valuesArray: Array<String>) {
        hoursNumberPicker.minValue = 0
        hoursNumberPicker.maxValue = valuesArray.size - 1
        hoursNumberPicker.displayedValues = valuesArray
        hoursNumberPicker.wrapSelectorWheel = true
    }

    override fun isChanged(): Boolean {
        val excludedIntervalsInitial = settingsViewModel.excludedIntervalsLiveData.value
        val excludedIntervalsFinal = calculateIntervals()

        if (excludedIntervalsInitial != null) {
            for (i in excludedIntervalsInitial.indices) {
                if (excludedIntervalsInitial[i] != excludedIntervalsFinal[i])
                    return true
            }
        }

        return false
    }

    /**
     * Проверяет корректность заданных интервалов (начальное время должно быть
     * меньше конечного). При этом проверяются только активные интервалы
     * (у которых установлен флажок)
     * @return Истина, если все активные интервалы корректны.
     * Ложь, если хотя бы один активный интервал некорректен.
     */
    private fun isIntervalsCorrect(): Boolean {
        val excludedIntervals: List<Interval> = calculateIntervals()
        for (interval in excludedIntervals) {
            if (interval.isEnabled) {
                if (!isCorrect(interval)) {
                    return false
                }
            }
        }
        return true
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

    private fun calculateIntervals(): List<Interval> {
        with(binding) {
            val result: MutableList<Interval> = ArrayList()
            val interval1 = calculateInterval(
                checkBox1,
                hoursNumberPickerStart1,
                minutesNumberPickerStart1,
                hoursNumberPickerEnd1,
                minutesNumberPickerEnd1
            )
            result.add(interval1)
            val interval2 = calculateInterval(
                checkBox2,
                hoursNumberPickerStart2,
                minutesNumberPickerStart2,
                hoursNumberPickerEnd2,
                minutesNumberPickerEnd2
            )
            result.add(interval2)
            val interval3 = calculateInterval(
                checkBox3,
                hoursNumberPickerStart3,
                minutesNumberPickerStart3,
                hoursNumberPickerEnd3,
                minutesNumberPickerEnd3
            )
            result.add(interval3)
            return result
        }
    }

    private fun calculateInterval(
        checkBox: CheckBox,
        hoursPickerStart: NumberPicker,
        minutesPickerStart: NumberPicker,
        hoursPickerEnd: NumberPicker,
        minutesPickerEnd: NumberPicker
    ): Interval {
        val startTime = calculateTime(hoursPickerStart, minutesPickerStart)
        val endTime = calculateTime(hoursPickerEnd, minutesPickerEnd)
        val isEnabled = checkBox.isChecked
        return Interval(startTime = startTime, endTime = endTime, isEnabled = isEnabled)
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

    override fun saveAndGoBack(view: View) {
        // Перед сохранением проверяем корректность интервалов.
        // Если интервалы корректны, сохраняем и выходим.
        // Иначе - отображаем диалоговое окно с сообщением.
        if (isIntervalsCorrect()) {
            val excludedIntervalsFinal = calculateIntervals()
            settingsViewModel.updateExcludedIntervals(excludedIntervalsFinal)
            goBack(view)
        } else {
            AlertUtils.showError(requireContext(), getString(R.string.message_incorrect_interval))
        }
    }
}