package com.binar.secondhand.kel2.ui.notification

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.databinding.NotificationContentBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class NotificationAdapter (private val onItemClick: OnClickListener) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<GetNotificationResponse.GetNotificationResponseItem>(){
        override fun areItemsTheSame(
            oldItem: GetNotificationResponse.GetNotificationResponseItem,
            newItem: GetNotificationResponse.GetNotificationResponseItem
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: GetNotificationResponse.GetNotificationResponseItem,
            newItem: GetNotificationResponse.GetNotificationResponseItem
        ): Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<GetNotificationResponse.GetNotificationResponseItem>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(NotificationContentBinding.inflate(inflater, parent,false))
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: NotificationContentBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(data: GetNotificationResponse.GetNotificationResponseItem){
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
                    .load(data.imageUrl)
                    .centerCrop()
                    .placeholder(shimmerDrawable)
                    .transform(CenterCrop(), RoundedCorners(12))
                    .into(binding.ivProduct)
                tvTitle.text =  data.product.name
                val formatter: NumberFormat = DecimalFormat("#,###")
                val myNumber = data.product.basePrice
                val formattedNumber: String = formatter.format(myNumber).toString()
                tvPrice.text = "Rp ${formattedNumber}"
                when (data.status) {
                    "create" -> {
                        tvNego.visibility = View.GONE
                        tvStatus.text = "Berhasil diterbitkan"
                    }
                    "accepted" -> {
                        val formatter: NumberFormat = DecimalFormat("#,###")
                        val myNumber = data.bidPrice
                        val formattedNumber: String = formatter.format(myNumber).toString()
                        tvNego.text = "Berhasil ditawar Rp ${formattedNumber}"
                        tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        tvStatus.text = "Penawaran produk"
                    }
                    else -> {
                        val formatter: NumberFormat = DecimalFormat("#,###")
                        val myNumber = data.bidPrice
                        val formattedNumber: String = formatter.format(myNumber).toString()
                        tvNego.text = "Ditawar Rp ${formattedNumber}"
                        tvStatus.text = "Penawaran produk"
                    }
                }
                tvTime.text = DateFormat.getDateInstance(DateFormat.FULL).format(date)
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
            }
        }
    }
    interface OnClickListener{
        fun onClickItem(data: GetNotificationResponse.GetNotificationResponseItem)
    }
}