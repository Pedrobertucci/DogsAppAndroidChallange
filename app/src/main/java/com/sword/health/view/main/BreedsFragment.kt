package com.sword.health.view.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sword.health.databinding.FragmentBreedsBinding
import com.sword.health.models.Breed
import com.sword.health.utils.Constant
import com.sword.health.view.utils.OnClickBreedCallback
import com.sword.health.view.adapter.BreedsAdapter
import com.sword.health.view.utils.ProgressDialog
import com.sword.health.viewModels.BreedViewModel
import javax.inject.Inject

class BreedsFragment : Fragment() {
    private lateinit var binding: FragmentBreedsBinding
    private lateinit var adapter: BreedsAdapter
    private lateinit var gridLayoutManager : GridLayoutManager
    private var breedsList = ArrayList<Breed>()
    private var page = 1
    private lateinit var toast: Toast
    private var disableGetBreeds = false
    private var pastVisibleItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    @Inject
    lateinit var viewModel: BreedViewModel

    override fun onAttach(context: Context) {
        (activity as MainActivity).applicationComponent.inject(breedFragment = this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProgressDialog.init(requireContext())
        setupAdapter()
        setupObservers()
        getBreeds()
    }

    private val onClickBreedCallback = object : OnClickBreedCallback {
        override fun onClick(breed: Breed) {
            val intent = Intent(requireContext(), BreedProfileActivity::class.java)
            intent.putExtra(Constant.argsBreed, breed)
            (requireActivity() as MainActivity).startActivity(intent)
        }
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            visibleItemCount = gridLayoutManager.childCount
            totalItemCount = gridLayoutManager.itemCount
            pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition()

            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                getBreeds()
            }
        }
    }

    private fun setupAdapter() {
        gridLayoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.addOnScrollListener(onScrollListener)
        adapter = BreedsAdapter(breedsList, onClickBreedCallback)
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        viewModel.breedsLiveData.observe(requireActivity() as MainActivity, {
            it?.let {
                if (it.isNotEmpty()) {
                    page++
                    breedsList.addAll(it)
                    adapter.notifyDataSetChanged()
                } else {
                    disableGetBreeds = true
                }
            }
        })

        viewModel.errorLiveData.observe(requireActivity() as MainActivity, {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.loadingLiveData.observe(requireActivity() as MainActivity, {
            it?.let {
                if(it) ProgressDialog.show() else ProgressDialog.hide()
            }
        })
    }

    private fun getBreeds() {
        if (!disableGetBreeds)
            viewModel.getBreeds(page = page)
    }

    override fun onPause() {
        super.onPause()
        ProgressDialog.hide()
    }
}