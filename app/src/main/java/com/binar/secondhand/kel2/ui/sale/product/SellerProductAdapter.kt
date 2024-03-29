package com.binar.secondhand.kel2.ui.sale.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetProductResponseItem
import com.binar.secondhand.kel2.databinding.ProductSaleListLayoutBinding
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import java.text.DecimalFormat
import java.text.NumberFormat

class SellerProductAdapter(
    private val onClick: (GetProductResponseItem, Int) -> Unit,
    private val delete: (Int) -> Unit,
    private val edit: (GetProductResponseItem, Int) -> Unit
) :
    ListAdapter<GetProductResponseItem, SellerProductAdapter.ViewHolder>(CommunityComparator()) {

    class ViewHolder(private val binding: ProductSaleListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currentGetProductResponseItem: GetProductResponseItem,
            onClick: (GetProductResponseItem, Int) -> Unit,
            delete: (Int) -> Unit,
            edit: (GetProductResponseItem, Int) -> Unit,
            position: Int
        ) {

            if (position == 0) {
                binding.clAddProduct.visibility = View.VISIBLE
                binding.clProductItem.visibility = View.INVISIBLE
            } else {
                binding.clAddProduct.visibility = View.INVISIBLE
                binding.clProductItem.visibility = View.VISIBLE
            }

            binding.btnHapus.setOnClickListener {
                delete(currentGetProductResponseItem.id)
            }

            binding.btnUbah.setOnClickListener {
                edit(currentGetProductResponseItem, currentGetProductResponseItem.id)
            }

            binding.root.setOnClickListener {
                onClick(currentGetProductResponseItem, position)
            }
            val shimmer =
                Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                    .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                    .setBaseAlpha(0.7f) //the alpha of the underlying children
                    .setHighlightAlpha(0.6f) // the shimmer alpha amount
                    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                    .setAutoStart(true)
                    .build()
            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }
            Glide.with(binding.root).load(currentGetProductResponseItem.imageUrl)
                .placeholder(shimmerDrawable)
                .into(binding.imvProductImage)
            binding.tvProductName.text = currentGetProductResponseItem.name
            binding.tvProductCategory.text =
                currentGetProductResponseItem.categories?.joinToString {
                    it.name
                }
            val formatter: NumberFormat = DecimalFormat("#,###")
            val myNumber = currentGetProductResponseItem.basePrice
            val formattedNumber: String = formatter.format(myNumber).toString()
            binding.tvProductPrice.text = "Rp. ${formattedNumber}"
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
        val binding = ProductSaleListLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick, delete, edit, position)
    }

    fun submitData(listProduct: List<GetProductResponseItem>) {
        val data = mutableListOf<GetProductResponseItem>()
        data.add(
            GetProductResponseItem(
                0,
                listOf(),
                "",
                0,
                "",
                "",
                "",
                "",
                "",
                0,
                ""
            )
        )
        data.addAll(listProduct)
        submitList(data)
    }

}