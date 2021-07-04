package com.sword.health

import android.app.Application
import com.sword.health.di.ApplicationComponent
import com.sword.health.di.DaggerApplicationComponent

class MainApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().application(this).build()
    }
}