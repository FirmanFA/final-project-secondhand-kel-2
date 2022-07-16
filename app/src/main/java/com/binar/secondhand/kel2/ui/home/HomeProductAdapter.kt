package com.binar.secondhand.kel2.ui.home

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponseItem
import com.binar.secondhand.kel2.databinding.HomeProductListLayoutBinding
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import java.text.DecimalFormat
import java.text.NumberFormat


class HomeProductAdapter(private val onClick: (GetProductResponseItem) -> Unit) :
    PagingDataAdapter<UiModel.ProductItem, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val uiModel = getItem(position)) {
            is UiModel.ProductItem -> (holder as RepoViewHolder).bind(uiModel.products)
            else -> {}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = HomeProductListLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return RepoViewHolder(binding)
    }

    inner class RepoViewHolder(private val binding: HomeProductListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var product: GetProductResponseItem? = null

        fun bind(productResponseItem: GetProductResponseItem) {

            this.product = productResponseItem

            binding.root.setOnClickListener {
                onClick(productResponseItem)
            }
            val shimmer = Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.7f) //the alpha of the underlying children
                .setHighlightAlpha(0.6f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()
            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }
            Glide.with(binding.root).load(productResponseItem.imageUrl)
                .placeholder(shimmerDrawable)
                .error(R.drawable.rectangle_camera)
                .into(binding.imvProductImage)
            binding.tvProductName.text = productResponseItem.name
            binding.tvProductCategory.text = productResponseItem.categoryString
            val formatter: NumberFormat = DecimalFormat("#,###")
            val myNumber = productResponseItem.basePrice.toInt()
            val formattedNumber: String = formatter.format(myNumber).toString()
            //formattedNumber is equal to 1,000,000
            binding.tvProductPrice.text = "Rp. $formattedNumber"
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<UiModel.ProductItem>() {
            override fun areItemsTheSame(oldItem: UiModel.ProductItem, newItem: UiModel.ProductItem): Boolean {
                return (oldItem.products.name == newItem.products.name)
            }

            override fun areContentsTheSame(oldItem: UiModel.ProductItem, newItem: UiModel.ProductItem): Boolean =
                oldItem == newItem
        }
    }
}
