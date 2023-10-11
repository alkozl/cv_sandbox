package com.example.customviewsampleapp.app

import android.app.Application
import com.example.customviewsampleapp.di.component.AppComponent

class App : Application() {
    private var _appComponent: AppComponent? = null
    val appComponent get() = _appComponent!!

    override fun onCreate() {
        super.onCreate()
        _appComponent = AppComponent()
    }
}