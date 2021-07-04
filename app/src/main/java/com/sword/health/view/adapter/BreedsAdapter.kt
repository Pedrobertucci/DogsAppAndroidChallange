package com.sword.health.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sword.health.databinding.BreedCardBinding
import com.sword.health.models.Breed
import com.sword.health.view.utils.OnClickBreedCallback

class BreedsAdapter(private val breedsList: ArrayList<Breed>, private val OnClickBreedCallback: OnClickBreedCallback) :
    RecyclerView.Adapter<BreedsAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: BreedCardBinding) :
                                                            RecyclerView.ViewHolder(itemBinding.root) {
      fun bind(breed: Breed) {
          itemBinding.txtName.text = breed.name
          itemBinding.root.preventCornerOverlap = false

          Picasso.get().load(breed.image.url).into(itemBinding.imageView)

          itemBinding.root.setOnClickListener { OnClickBreedCallback.onClick(breed) }
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = BreedCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(breedsList[position])
    }

    override fun getItemCount(): Int = breedsList.size
}