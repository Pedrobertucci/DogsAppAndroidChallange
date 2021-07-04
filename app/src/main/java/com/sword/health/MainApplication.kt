package com.sword.health

import android.app.Application
import com.sword.health.di.ApplicationComponent
import com.sword.health.di.DaggerApplicationComponent

class MainApplication : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create()
    }
}