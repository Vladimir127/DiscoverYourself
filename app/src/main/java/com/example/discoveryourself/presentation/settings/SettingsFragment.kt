package com.example.discoveryourself.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.discoveryourself.R
import com.example.discoveryourself.presentation.MainActivity

class SettingsFragment : Fragment() {

    private lateinit var intervalRel: RelativeLayout
    private lateinit var excludedIntervalsRel: RelativeLayout
    private lateinit var countRel: RelativeLayout
    private lateinit var signalTypeRel: RelativeLayout
    private lateinit var volumeRel: RelativeLayout
    private lateinit var imageRel: RelativeLayout
    private lateinit var soundRel: RelativeLayout
    private lateinit var textRel: RelativeLayout

    private lateinit var countTextView: TextView
    private lateinit var saveTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setInfo()
    }

    private fun initViews(view: View) {
        countTextView = view.findViewById(R.id.CountTextView)

        intervalRel = view.findViewById(R.id.IntervalRel)
        excludedIntervalsRel = view.findViewById(R.id.ExcludedIntervalRel)
        countRel = view.findViewById(R.id.CountRel)
        signalTypeRel = view.findViewById(R.id.SignalTypeRel)
        volumeRel = view.findViewById(R.id.VolumeRel)
        imageRel = view.findViewById(R.id.ImageRel)
        soundRel = view.findViewById(R.id.SoundRel)
        textRel = view.findViewById(R.id.TextRel)
        saveTextView = view.findViewById(R.id.SaveTextView)

        intervalRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_intervalFragment)}
        excludedIntervalsRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_excludedIntervalsFragment)}
        countRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_countFragment)}
        signalTypeRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_typeFragment)}
        volumeRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_volumeFragment)}
        imageRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_chooseImageFragment)}
        soundRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_chooseAudioFragment)}
        textRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_chooseTextFragment)}
        saveTextView.setOnClickListener{saveAndGoBack(it)}
    }

    private fun setInfo() {
        countTextView.text = getString(R.string.count_per_hour, (activity as MainActivity).alarmSettings.count)
    }

    private fun saveAndGoBack(view: View) {
        (activity as MainActivity).saveAlarmSettings()
    }
}