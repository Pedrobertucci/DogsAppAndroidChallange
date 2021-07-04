package com.sword.health.di

import android.app.Application
import com.sword.health.view.BreedsFragment
import com.sword.health.view.MainActivity
import com.sword.health.view.SearchBreedFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun application(application: Application): Builder
    }

    fun inject(mainActivity: MainActivity)
    fun inject(breedFragment: BreedsFragment)
    fun inject(searchBreedFragment: SearchBreedFragment)
}