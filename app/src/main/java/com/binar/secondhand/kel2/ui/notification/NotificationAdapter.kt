package com.binar.secondhand.kel2.ui.notification

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.databinding.NotificationContentBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class NotificationAdapter (private val onItemClick: OnClickListener, private val product: List<GetProductIdResponse>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

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
        data.let { holder.bind(data, product[position]) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: NotificationContentBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(data: GetNotificationResponse.GetNotificationResponseItem, product: GetProductIdResponse){
            binding.apply {
                val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'", Locale.ROOT)
                val date = format.parse(data.transactionDate) as Date

                Glide.with(binding.root)
                    .load(data.imageUrl)
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                    .into(binding.ivProduct)
                tvTitle.text =  product.name
                tvPrice.text = product.basePrice.toString()
                tvNego.text = data.bidPrice.toString()
                tvStatus.text = data.status
                tvTime.text = DateFormat.getDateInstance(DateFormat.FULL).format(date)
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
                Log.d("list adapter", "bind: $data $product")
            }
        }
    }
    interface OnClickListener{
        fun onClickItem(data: GetNotificationResponse.GetNotificationResponseItem)
    }
}