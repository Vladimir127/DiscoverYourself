package com.example.discoveryourself.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.example.discoveryourself.utils.AlertUtils
import com.example.discoveryourself.utils.DialogButtonClickListener

abstract class BaseSettingFragment<T : ViewBinding>(private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T) :
    Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModelProvider = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )
        settingsViewModel = viewModelProvider[SettingsViewModel::class.java]

        _binding = bindingInflater.invoke(layoutInflater, container, false)
        val view = binding.root

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setSave(view)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return view
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun initViews()

    /**
     * Осуществляет переход к предыдущему фрагменту
     */
    protected fun goBack(view: View) {
        view.findNavController().popBackStack()
    }

    abstract fun isChanged(): Boolean

    fun setSave(view: View) {
        if (isChanged()) {
            AlertUtils.showSaveChanges(requireContext(), object : DialogButtonClickListener {
                override fun onYesButtonClick() {
                    // Сохранить
                    saveAndGoBack(view)
                }

                override fun onNoButtonClick() {
                    // Не сохранять
                    goBack(view)
                }
            })
        } else {
            goBack(view)
        }
    }

    abstract fun saveAndGoBack(view: View)
}