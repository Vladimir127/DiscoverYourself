package com.example.discoveryourself.presentation.settings

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.example.discoveryourself.R
import com.example.discoveryourself.databinding.FragmentVolumeBinding

class VolumeFragment : BaseSettingFragment<FragmentVolumeBinding>(FragmentVolumeBinding::inflate) {

    private var progress1 = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsViewModel.volumeLiveData.observe(viewLifecycleOwner) { volume ->
            setVolume(volume)
        }
    }

    override fun initViews() {
        binding.VolumeBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                if (progress < 75) {
                    seekBar.progress = 75
                }
                if (progress > 925) {
                    seekBar.progress = 925
                }
                val progressFraction = (progress - 75).toDouble() / (925 - 75)
                var progressPercent = (progressFraction * 100).toInt()
                if (progressPercent < 0) {
                    progressPercent = 0
                }
                if (progressPercent > 100) {
                    progressPercent = 100
                }
                binding.SoundTextView.text = requireContext().resources.getString(R.string.volume_percent, progressPercent)
                progress1 = progressPercent
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        binding.MenuImageView.setOnClickListener{setSave(it)}
        binding.CanselTextView.setOnClickListener{goBack(it)}
        binding.SaveTextView.setOnClickListener{saveAndGoBack(it)}
    }

    private fun setVolume(volume: Int) {
        progress1 = volume

        val progressFraction = volume.toDouble() / 100
        val progress = progressFraction * (925 - 75) + 75

        binding.VolumeBar.progress = progress.toInt()
        binding.SoundTextView.text = requireContext().resources.getString(R.string.volume_percent, volume)
    }

    override fun isChanged(): Boolean {
        val volumeInitial = settingsViewModel.volumeLiveData.value
        val volumeFinal = progress1
        return volumeInitial != volumeFinal
    }

    override fun saveAndGoBack(view: View) {
        settingsViewModel.updateVolume(progress1)
        goBack(view)
    }
}