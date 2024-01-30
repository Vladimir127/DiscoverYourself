package com.example.discoveryourself.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.discoveryourself.R

class SettingsFragment : Fragment() {

    private lateinit var intervalRel: RelativeLayout
    private lateinit var excludedIntervalsRel: RelativeLayout
    private lateinit var countRel: RelativeLayout
    private lateinit var signalTypeRel: RelativeLayout
    private lateinit var volumeRel: RelativeLayout
    private lateinit var imageRel: RelativeLayout
    private lateinit var soundRel: RelativeLayout
    private lateinit var textRel: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRandomSignal(view)
    }

    private fun initializeRandomSignal(view: View) {
        intervalRel = view.findViewById(R.id.IntervalRel)
        excludedIntervalsRel = view.findViewById(R.id.ExcludedIntervalRel)
        countRel = view.findViewById(R.id.CountRel)
        signalTypeRel = view.findViewById(R.id.SignalTypeRel)
        volumeRel = view.findViewById(R.id.VolumeRel)
        imageRel = view.findViewById(R.id.ImageRel)
        soundRel = view.findViewById(R.id.SoundRel)
        textRel = view.findViewById(R.id.TextRel)

        intervalRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_intervalFragment)}
        excludedIntervalsRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_excludedIntervalsFragment)}
        countRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_countFragment)}
        signalTypeRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_typeFragment)}
        volumeRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_volumeFragment)}
        imageRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_chooseImageFragment)}
        soundRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_chooseAudioFragment)}
        textRel.setOnClickListener { it.findNavController().navigate(R.id.action_settingsFragment_to_chooseTextFragment)}
    }
}