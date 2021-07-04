package com.sword.health.di

import com.sword.health.view.BreedsFragment
import com.sword.health.view.MainActivity
import com.sword.health.view.SearchBreedFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(): ApplicationComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(breedFragment: BreedsFragment)
    fun inject(searchBreedFragment: SearchBreedFragment)
}