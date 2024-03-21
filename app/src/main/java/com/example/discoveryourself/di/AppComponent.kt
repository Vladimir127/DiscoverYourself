package com.example.discoveryourself.di

import com.example.discoveryourself.presentation.settings.SettingsViewModel
import dagger.Component

@Component(modules = [DbModule::class])
interface AppComponent {
    fun inject(settingsViewModel: SettingsViewModel)
}