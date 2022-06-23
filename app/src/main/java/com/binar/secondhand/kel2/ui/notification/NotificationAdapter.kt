package com.binar.secondhand.kel2.ui.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.databinding.NotificationContentBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

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
//                tvPrice.text = data.bidPrice.toString()
                Glide.with(binding.root)
                    .load(data.imageUrl)
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                    .into(binding.ivProduct)
                tvTitle.text = "Lorem Ipsum"
                tvPrice.text = "Free"
                tvNego.text = data.bidPrice.toString()
                tvStatus.text = data.status
                tvTime.text = data.transactionDate
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