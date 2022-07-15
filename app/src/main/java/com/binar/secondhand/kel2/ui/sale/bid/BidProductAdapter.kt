package com.binar.secondhand.kel2.ui.sale.bid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.GetOrderResponse
import com.binar.secondhand.kel2.databinding.NotificationContentBinding
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

class BidProductAdapter (private val onItemClick: OnClickListener) : RecyclerView.Adapter<BidProductAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<GetOrderResponse.GetOrderResponseItem>(){
        override fun areItemsTheSame(
            oldItem: GetOrderResponse.GetOrderResponseItem,
            newItem: GetOrderResponse.GetOrderResponseItem
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: GetOrderResponse.GetOrderResponseItem,
            newItem: GetOrderResponse.GetOrderResponseItem
        ): Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<GetOrderResponse.GetOrderResponseItem>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BidProductAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(NotificationContentBinding.inflate(inflater, parent,false))
    }

    override fun onBindViewHolder(holder: BidProductAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: NotificationContentBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(data: GetOrderResponse.GetOrderResponseItem){
            binding.apply {
                val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'", Locale.ROOT)
                val date = format.parse(data.transactionDate) as Date

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
                val formatter: NumberFormat = DecimalFormat("#,###")
                val myNumber = data.product.basePrice
                val myNumber2 = data.price
                val formattedNumber: String = formatter.format(myNumber).toString()
                val formattedNumber2: String = formatter.format(myNumber2).toString()

                Glide.with(binding.root)
                    .load(data.imageProduct)
                    .centerCrop()
                    .placeholder(shimmerDrawable)
                    .transform(CenterCrop(), RoundedCorners(16))
                    .into(binding.ivProduct)
                tvTitle.text =  data.product.name
                tvPrice.text = "Rp $formattedNumber"
                tvNego.text = "Rp $formattedNumber2"
                tvStatus.text = data.status
                tvTime.text = DateFormat.getDateInstance(DateFormat.FULL).format(date)
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
            }
        }
    }
    interface OnClickListener{
        fun onClickItem(data: GetOrderResponse.GetOrderResponseItem)
    }
}