package com.binar.secondhand.kel2.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponseItem
import com.binar.secondhand.kel2.databinding.HomeProductListLayoutBinding
import com.bumptech.glide.Glide

class HomeProductAdapter(private val onClick: (GetProductResponseItem) -> Unit) :
    ListAdapter<GetProductResponseItem, HomeProductAdapter.ViewHolder>(CommunityComparator()) {


    class ViewHolder(private val binding: HomeProductListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currentGetProductResponseItem: GetProductResponseItem,
            onClick: (GetProductResponseItem) -> Unit
        ) {
            binding.root.setOnClickListener {
                onClick(currentGetProductResponseItem)
            }
            Glide.with(binding.root).load(currentGetProductResponseItem.imageUrl)
                .into(binding.imvProductImage)
            binding.tvProductName.text = currentGetProductResponseItem.name
            binding.tvProductCategory.text = currentGetProductResponseItem.name
            binding.tvProductPrice.text = "Rp. ${currentGetProductResponseItem.basePrice}"
        }

    }

    class CommunityComparator : DiffUtil.ItemCallback<GetProductResponseItem>() {
        override fun areItemsTheSame(
            oldItem: GetProductResponseItem,
            newItem: GetProductResponseItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: GetProductResponseItem,
            newItem: GetProductResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeProductListLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

}