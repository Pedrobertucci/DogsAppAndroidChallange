package com.sword.health.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sword.health.databinding.FragmentBreedsBinding

class BreedFragment : Fragment() {
    lateinit var binding: FragmentBreedsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedsBinding.inflate(inflater, container, false)
        return binding.root
    }
}