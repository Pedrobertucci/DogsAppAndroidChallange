package com.sword.health.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sword.health.databinding.BreedCardBinding
import com.sword.health.databinding.SearchCardBinding
import com.sword.health.models.Breed
import com.sword.health.view.utils.OnClickBreedCallback

class SearchAdapter(private val breedsList: ArrayList<Breed>, private val OnClickBreedCallback: OnClickBreedCallback) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: SearchCardBinding) :
                                                            RecyclerView.ViewHolder(itemBinding.root) {
      fun bind(breed: Breed) {
          itemBinding.txtNameValue.text = breed.name
          itemBinding.txtGroupValue.text = breed.breedGroup
          itemBinding.txtOriginValue.text = breed.origin

          itemBinding.root.setOnClickListener { OnClickBreedCallback.onClick(breed) }
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = SearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(breedsList[position])
    }

    override fun getItemCount(): Int = breedsList.size
}