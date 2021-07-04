package com.sword.health.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.sword.health.MainApplication
import com.sword.health.R
import com.sword.health.databinding.ActivityMainBinding
import com.sword.health.di.ApplicationComponent
import com.sword.health.viewModels.BreedViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var viewModel: BreedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        applicationComponent = (applicationContext as MainApplication).applicationComponent
        applicationComponent.inject(mainActivity = this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigation = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.activityMainBottomNavigationView, navigation)
    }
}