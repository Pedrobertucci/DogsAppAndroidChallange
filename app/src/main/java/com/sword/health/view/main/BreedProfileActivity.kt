package com.sword.health.view.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sword.health.MainApplication
import com.sword.health.databinding.ActivityBreedProfileBinding
import com.sword.health.di.ApplicationComponent
import com.sword.health.models.Breed
import com.sword.health.models.Image
import com.sword.health.utils.Constant
import com.sword.health.view.adapter.ImageAdapter
import com.sword.health.view.utils.ProgressDialog
import com.sword.health.viewModels.BreedViewModel
import javax.inject.Inject

class BreedProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBreedProfileBinding
    lateinit var applicationComponent: ApplicationComponent
    private var imageList = ArrayList<Image>()
    private lateinit var adapter: ImageAdapter

    @Inject
    lateinit var viewModel: BreedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        applicationComponent = (applicationContext as MainApplication).applicationComponent
        applicationComponent.inject(breedProfileActivity = this)
        super.onCreate(savedInstanceState)
        binding = ActivityBreedProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ProgressDialog.init(this)

        intent.getParcelableExtra<Breed>(Constant.argsBreed)?.let {
            setupObservers()
            setupData(it)
            setupAdapter()
            setupOnBack()
            viewModel.getBreedPhoto(breedId = it.id.toString())
        } ?: finish()
    }

    private fun setupAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this,
                                                        RecyclerView.HORIZONTAL, false)
        adapter = ImageAdapter(imageList)
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupData(breed: Breed) {
        imageList.add(breed.image)
        binding.txtTitle.text = breed.name
        binding.txtGroupValue.text = breed.breedGroup
        binding.txtOriginValue.text = breed.origin
        binding.txtTemperamentValue.text = breed.temperament
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        viewModel.photoLiveData.observe(this, {
            it?.let { it ->
                showDownloadImages(it)
            }
        })

        viewModel.loadingLiveData.observe(this, {
            it?.let {
                if(it) ProgressDialog.show() else ProgressDialog.hide()
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showDownloadImages(images: ArrayList<Image>) {
        imageList.addAll(images)
        val listWithoutDuplicates = imageList.distinct()
        images.clear()
        images.addAll(listWithoutDuplicates)
        adapter.notifyDataSetChanged()
    }

    private fun setupOnBack() {
        binding.txtTitle.setOnClickListener { onBackPressed() }
        binding.imgBack.setOnClickListener { onBackPressed() }
    }
}