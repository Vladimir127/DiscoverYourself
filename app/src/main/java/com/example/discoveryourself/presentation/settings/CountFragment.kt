package com.example.discoveryourself.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.discoveryourself.R
import com.example.discoveryourself.presentation.MainActivity
import com.example.discoveryourself.utils.Constants.RANDOM_SIGNAL_MAX_COUNT

class CountFragment : Fragment() {

    private lateinit var menuImageView: ImageView
    private lateinit var countNumberPicker: NumberPicker
    private lateinit var saveTextView: TextView
    private lateinit var cancelTextView: TextView

    private val alarmSettings by lazy { (activity as MainActivity).alarmSettings }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_count, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        countNumberPicker.value = alarmSettings.count
    }

    private fun initViews(view: View) {
        countNumberPicker = view.findViewById(R.id.CountNumberPicker)
        countNumberPicker.minValue = 1
        countNumberPicker.maxValue = RANDOM_SIGNAL_MAX_COUNT
        //countNumberPicker.value = settingsViewModel.count.value!!

        menuImageView = view.findViewById(R.id.MenuImageView)
        menuImageView.setOnClickListener{setSave()}

        cancelTextView = view.findViewById(R.id.CancelTextView)
        cancelTextView.setOnClickListener{goBack(it)}

        saveTextView = view.findViewById(R.id.SaveTextView)
        saveTextView.setOnClickListener{saveAndGoBack(it)}
        // TODO: Переопределить нажатие на аппаратную кнопку Назад.
    }

    private fun setSave() {
        // TODO: Проверить изменения и выдать диалоговое окно
    }

    private fun saveAndGoBack(view: View) {
        // TODO: Добавить проверку на максимальное количество сигналов
        //settingsViewModel.alarmSettings = countNumberPicker.value
        alarmSettings.count = countNumberPicker.value
        alarmSettings.changed = true
        goBack(view)
    }

    /**
     * Осуществляет переход к предыдущему фрагменту
     */
    private fun goBack(view: View) {
        view.findNavController().popBackStack()
    }
}