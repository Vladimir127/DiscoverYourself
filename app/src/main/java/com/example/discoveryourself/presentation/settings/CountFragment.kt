package com.example.discoveryourself.presentation.settings

import android.os.Bundle
import android.view.View
import com.example.discoveryourself.databinding.FragmentCountBinding
import com.example.discoveryourself.utils.Constants.RANDOM_SIGNAL_MAX_COUNT

class CountFragment : BaseSettingFragment<FragmentCountBinding>(FragmentCountBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsViewModel.countLiveData.observe(viewLifecycleOwner) { count ->
            binding.CountNumberPicker.value = count
        }
    }

    override fun initViews() {
        binding.CountNumberPicker.minValue = 1
        binding.CountNumberPicker.maxValue = RANDOM_SIGNAL_MAX_COUNT

        binding.MenuImageView.setOnClickListener{setSave(it)}
        binding.CancelTextView.setOnClickListener{goBack(it)}
        binding.SaveTextView.setOnClickListener{saveAndGoBack(it)}
    }

    override fun isChanged(): Boolean {
        val initialCount = settingsViewModel.countLiveData.value
        val currentCount = binding.CountNumberPicker.value

        return initialCount != currentCount
    }

    override fun saveAndGoBack(view: View) {
        // TODO: Добавить проверку на максимальное количество сигналов
        settingsViewModel.updateCount(binding.CountNumberPicker.value)
        goBack(view)
    }
}