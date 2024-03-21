package com.example.discoveryourself.presentation.settings

import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.discoveryourself.R
import com.example.discoveryourself.databinding.FragmentSettingsBinding
import com.example.discoveryourself.domain.models.AlarmSettings
import com.example.discoveryourself.domain.models.Interval
import com.example.discoveryourself.utils.TimeUtils

class SettingsFragment : BaseSettingFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun initViews() {
        with(binding) {
            IntervalRel.setOnClickListener {
                it.findNavController().navigate(R.id.action_settingsFragment_to_intervalFragment)
            }
            ExcludedIntervalRel.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_settingsFragment_to_excludedIntervalsFragment)
            }
            CountRel.setOnClickListener {
                it.findNavController().navigate(R.id.action_settingsFragment_to_countFragment)
            }
            SignalTypeRel.setOnClickListener {
                it.findNavController().navigate(R.id.action_settingsFragment_to_typeFragment)
            }
            VolumeRel.setOnClickListener {
                it.findNavController().navigate(R.id.action_settingsFragment_to_volumeFragment)
            }
            ImageRel.setOnClickListener {
                it.findNavController().navigate(R.id.action_settingsFragment_to_chooseImageFragment)
            }
            SoundRel.setOnClickListener {
                it.findNavController().navigate(R.id.action_settingsFragment_to_chooseAudioFragment)
            }
            TextRel.setOnClickListener {
                it.findNavController().navigate(R.id.action_settingsFragment_to_setTextFragment)
            }
            SaveTextView.setOnClickListener { saveAndGoBack(it) }
        }

        settingsViewModel.alarmSettings.observe(viewLifecycleOwner) { alarmSettings ->
            showData(alarmSettings)
        }
    }

    private fun showData(alarmSettings: AlarmSettings) {
        with(binding) {
            CountTextView.text = getString(R.string.count_per_hour, alarmSettings.count)
            SoundTextView.text = getString(R.string.volume_percent, alarmSettings.volume)
            TextTextView.text = alarmSettings.text
            setType(alarmSettings.type)

            val startTime = TimeUtils.createTimeString(alarmSettings.interval.startTime)
            val endTime = TimeUtils.createTimeString(alarmSettings.interval.endTime)
            IntervalTextView.text = getString(R.string.interval_from_to, startTime, endTime)

            setExcludedIntervals(alarmSettings.excludedIntervals)
        }
    }

    private fun setType(type: String) {
        with(binding) {
            if (type == "type1") {
                TypeSignalTextView.text = getString(R.string.type_signal_1)
                VolumeRel.visibility = View.VISIBLE
                SoundRel.visibility = View.VISIBLE
            }
            if (type == "type2") {
                TypeSignalTextView.text = getString(R.string.type_signal_2)
                VolumeRel.visibility = View.VISIBLE
                SoundRel.visibility = View.VISIBLE
            }
            if (type == "type3") {
                TypeSignalTextView.text = getString(R.string.type_signal_3)
                VolumeRel.visibility = View.GONE
                SoundRel.visibility = View.GONE
            }
            if (type == "type4") {
                TypeSignalTextView.text = getString(R.string.type_signal_4)
                VolumeRel.visibility = View.GONE
                SoundRel.visibility = View.GONE
            }
        }
    }

    /**
     * Формирует строку со списком исключённых интервалов и отображает её в элементе excludedIntervalTextView
     *
     * @param excludedIntervals коллекция исключённых интервалов, полученная из объекта SettingsViewModel.
     */
    private fun setExcludedIntervals(excludedIntervals: List<Interval>) {
        val excludedIntervalsString = buildString {
            for (interval in excludedIntervals) {
                if (interval.isEnabled) {
                    append(interval)
                    append("\n")
                }
            }
            if (isNotEmpty()) {
                deleteCharAt(length - 1)
            }
        }

        binding.excludedIntervalTextView.text = excludedIntervalsString
    }

    override fun saveAndGoBack(view: View) {
        settingsViewModel.saveSettings()
        Toast.makeText(requireContext(), "Настройки сохранены", Toast.LENGTH_SHORT).show()
        requireActivity().finish()
    }

    override fun isChanged(): Boolean {
        return settingsViewModel.isChanged
    }
}