package com.sword.health.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sword.health.databinding.BreedImageBinding
import com.sword.health.models.Image

class ImageAdapter(private val imageList: ArrayList<Image>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: BreedImageBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(image: Image) {
            Picasso.get().load(image.url).into(itemBinding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = BreedImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size
}