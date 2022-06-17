package com.binar.secondhand.kel2.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponseItem
import com.binar.secondhand.kel2.databinding.HomeProductListLayoutBinding

class HomeBannerAdapter(private val onClick: (GetBannerResponseItem) -> Unit) :
    ListAdapter<GetBannerResponseItem, HomeBannerAdapter.ViewHolder>(CommunityComparator()) {


    class ViewHolder(private val binding: HomeProductListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currentProduct: GetBannerResponseItem,
            onClick: (GetBannerResponseItem) -> Unit
        ) {


        }

    }

    class CommunityComparator : DiffUtil.ItemCallback<GetBannerResponseItem>() {
        override fun areItemsTheSame(oldItem: GetBannerResponseItem, newItem: GetBannerResponseItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GetBannerResponseItem, newItem: GetBannerResponseItem): Boolean {
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