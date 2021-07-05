package com.sword.health.view.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sword.health.databinding.FragmentSearchBreedBinding
import com.sword.health.models.Breed
import com.sword.health.utils.Constant
import com.sword.health.view.adapter.BreedsAdapter
import com.sword.health.view.adapter.SearchAdapter
import com.sword.health.view.main.MainActivity
import com.sword.health.view.utils.OnClickBreedCallback
import com.sword.health.view.utils.ProgressDialog
import com.sword.health.view.utils.Utils.hideKeyboard
import com.sword.health.view.utils.Utils.openKeyboard
import com.sword.health.viewModels.BreedViewModel
import javax.inject.Inject

class SearchBreedFragment : Fragment() {
    private lateinit var binding: FragmentSearchBreedBinding
    private var breedsList = ArrayList<Breed>()
    private lateinit var adapter: SearchAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProgressDialog.init(requireContext())
        setupAdapter()
        setupObservers()
        setupSearch()
    }

    override fun onResume() {
        super.onResume()
        binding.searchView.isIconified = false
    }

    override fun onPause() {
        super.onPause()
        binding.searchView.context.hideKeyboard(binding.root)
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let {
                    if (query.length > 2) {
                        viewModel.getBreedsByName(query)
                    } else {
                        breedsList.clear()
                        adapter.notifyDataSetChanged()
                    }
                }
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (newText.length > 2) {
                        viewModel.getBreedsByName(newText)
                    } else {
                        breedsList.clear()
                        adapter.notifyDataSetChanged()
                    }
                }
                return true
            }
        })
    }

    private fun setupAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter(breedsList, onClickBreedCallback)
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        viewModel.breedsLiveData.observe(requireActivity() as MainActivity, {
            it?.let {
                if (it.isNotEmpty()) {
                    hideEmptyData()
                    breedsList.clear()
                    breedsList.addAll(it)
                    adapter.notifyDataSetChanged()
                } else {
                    if (breedsList.isEmpty())
                        showEmptyData()
                }
            }
        })

        viewModel.errorLiveData.observe(requireActivity() as MainActivity, {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun hideEmptyData() {
       if (binding.txtEmptyData.isVisible)
           binding.txtEmptyData.visibility = View.INVISIBLE
    }

    private fun showEmptyData() {
        if (!binding.txtEmptyData.isVisible)
            binding.txtEmptyData.visibility = View.VISIBLE
    }

    private val onClickBreedCallback = object : OnClickBreedCallback {
        override fun onClick(breed: Breed) {
            val intent = Intent(requireContext(), BreedProfileActivity::class.java)
            intent.putExtra(Constant.argsBreed, breed)
            (requireActivity() as MainActivity).startActivity(intent)
        }
    }
}