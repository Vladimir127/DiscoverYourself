package com.example.discoveryourself.presentation.settings

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.discoveryourself.R
import com.example.discoveryourself.databinding.FragmentSetTextBinding

class SetTextFragment : BaseSettingFragment<FragmentSetTextBinding>(FragmentSetTextBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsViewModel.textLiveData.observe(viewLifecycleOwner) { text ->
            binding.EditTextView.setText(text)
        }
    }

    override fun initViews() {
        binding.EditTextView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    enableSaveButton(false)
                } else {
                    enableSaveButton(true)
                }
            }

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })

        binding.MenuImageView.setOnClickListener{setSave(it)}
        binding.CanselTextView.setOnClickListener{goBack(it)}
        binding.SaveTextView.setOnClickListener{saveAndGoBack(it)}
    }

    private fun enableSaveButton(value: Boolean) {
        with(binding) {
            if (value) {
                SaveTextView.setTextColor(resources.getColor(R.color.main))
                SaveTextView.setOnClickListener {
                    if (EditTextView.text.toString() != "") {
                        saveAndGoBack(SaveTextView)
                    }
                }
            } else {
                SaveTextView.setTextColor(resources.getColor(R.color.icon_disabled))
                SaveTextView.setOnClickListener { }
            }
        }
    }

    override fun isChanged(): Boolean {
        val textInitial = settingsViewModel.textLiveData.value
        val textFinal: String = binding.EditTextView.text.toString()
        return textInitial != textFinal
    }

    override fun saveAndGoBack(view: View) {
        settingsViewModel.updateText(binding.EditTextView.text.toString())
        goBack(view)
    }
}