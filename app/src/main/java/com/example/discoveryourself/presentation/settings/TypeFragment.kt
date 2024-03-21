package com.example.discoveryourself.presentation.settings

import android.view.View
import com.example.discoveryourself.databinding.FragmentTypeBinding

class TypeFragment : BaseSettingFragment<FragmentTypeBinding>(FragmentTypeBinding::inflate) {

    override fun initViews() {
        with(binding) {
            MenuImageView.setOnClickListener { setSave(it) }
            CanselTextView.setOnClickListener { goBack(it) }
            SaveTextView.setOnClickListener { saveAndGoBack(it) }

            settingsViewModel.typeLiveData.observe(viewLifecycleOwner) { type ->
                when (type) {
                    "type1" -> RadioButton1.isChecked = true
                    "type2" -> RadioButton2.isChecked = true
                    "type3" -> RadioButton3.isChecked = true
                    "type4" -> RadioButton4.isChecked = true
                }
            }

            Rel1.setOnClickListener {
                clearRadio()
                RadioButton1.isChecked = true
            }
            Rel2.setOnClickListener {
                clearRadio()
                RadioButton2.isChecked = true
            }
            Rel3.setOnClickListener {
                clearRadio()
                RadioButton3.isChecked = true
            }
            Rel4.setOnClickListener {
                clearRadio()
                RadioButton4.isChecked = true
            }
        }
    }

    private fun clearRadio() {
        with(binding) {
            RadioButton1.isChecked = false
            RadioButton2.isChecked = false
            RadioButton3.isChecked = false
            RadioButton4.isChecked = false
        }
    }

    private fun getType(): String {
        with(binding) {
            var rez = ""
            if (RadioButton1.isChecked) {
                rez = "type1"
            }
            if (RadioButton2.isChecked) {
                rez = "type2"
            }
            if (RadioButton3.isChecked) {
                rez = "type3"
            }
            if (RadioButton4.isChecked) {
                rez = "type4"
            }
            return rez
        }
    }

    override fun isChanged(): Boolean {
        val typeInitial = settingsViewModel.typeLiveData.value
        val typeFinal = getType()
        return typeInitial != typeFinal
    }

    override fun saveAndGoBack(view: View) {
        settingsViewModel.updateType(getType())
        goBack(view)
    }
}