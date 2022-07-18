package com.binar.secondhand.kel2.ui.wishlist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.wishlist.get.GetWishlistItem
import com.binar.secondhand.kel2.databinding.WishlistItemBinding
import com.binar.secondhand.kel2.ui.notification.NotificationAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class WishlistAdapter (private  val onItemClick: OnClickListener): RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {



    private val diffCallback = object : DiffUtil.ItemCallback<GetWishlistItem>(){
        override fun areItemsTheSame(
            oldItem: GetWishlistItem,
            newItem: GetWishlistItem,
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: GetWishlistItem,
            newItem: GetWishlistItem,
        ): Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<GetWishlistItem>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(WishlistItemBinding.inflate(inflater, parent,false))
    }

    override fun onBindViewHolder(holder: WishlistAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: WishlistItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(data: GetWishlistItem){
            binding.apply {
                val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'", Locale.ROOT)
                val date = format.parse(data.createdAt) as Date

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

                Glide.with(binding.root)
                    .load(data.Product.image_url)
                    .error(R.drawable.ic_broken)
                    .centerCrop()
                    .placeholder(shimmerDrawable)
                    .transform(CenterCrop(), RoundedCorners(12))
                    .into(binding.ivProduct)
                tvTitle.text =  data.Product.name
                val formatter: NumberFormat = DecimalFormat("#,###")
                val myNumber = data.Product.base_price.toInt()
                val formattedNumber: String = formatter.format(myNumber).toString()
                tvPrice.text = "Rp $formattedNumber"
                tvLocation.text = data.Product.location

                tvTime.text = DateFormat.getDateInstance(DateFormat.FULL).format(date)
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
            }
        }
    }

    interface OnClickListener{
        fun onClickItem(data: GetWishlistItem)
    }



}