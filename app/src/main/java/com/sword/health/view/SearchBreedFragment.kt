package com.sword.health.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sword.health.databinding.FragmentSearchBreedBinding
import com.sword.health.viewModels.BreedViewModel
import javax.inject.Inject

class SearchBreedFragment : Fragment() {
    private lateinit var binding: FragmentSearchBreedBinding

    @Inject
    lateinit var viewModel: BreedViewModel

    override fun onAttach(context: Context) {
        (activity as MainActivity).applicationComponent.inject(searchBreedFragment = this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBreedBinding.inflate(inflater, container, false)
        return binding.root
    }
}