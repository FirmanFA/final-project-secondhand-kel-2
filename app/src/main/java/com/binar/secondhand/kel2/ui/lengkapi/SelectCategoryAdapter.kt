package com.binar.secondhand.kel2.ui.lengkapi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponseItem
import com.binar.secondhand.kel2.databinding.SelectCategoryListLayoutBinding

class SelectCategoryAdapter(private val onClick: (GetCategoryResponseItem,Boolean, Int) -> Unit) :
    RecyclerView.Adapter<SelectCategoryAdapter.ViewHolder>() {

    private val diffCallback =
        object : DiffUtil.ItemCallback<Pair<Boolean,GetCategoryResponseItem>>() {
            override fun areItemsTheSame(
                oldItem: Pair<Boolean,GetCategoryResponseItem>,
                newItem: Pair<Boolean,GetCategoryResponseItem>
            ): Boolean = oldItem.second.id == newItem.second.id

            override fun areContentsTheSame(
                oldItem: Pair<Boolean,GetCategoryResponseItem>,
                newItem: Pair<Boolean,GetCategoryResponseItem>
            ): Boolean = oldItem.hashCode() == newItem.hashCode()
        }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<Pair<Boolean,GetCategoryResponseItem>>?) =
        differ.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectCategoryAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(SelectCategoryListLayoutBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: SelectCategoryAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data, position) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: SelectCategoryListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Pair<Boolean,GetCategoryResponseItem>, position: Int) {
//            binding.cbCategory.setOnCheckedChangeListener { _, isChecked ->
//                onClick(category.second, isChecked, position)
//            }
            binding.cbCategory.setOnClickListener {
                onClick(category.second, binding.cbCategory.isChecked, position)
            }
            binding.cbCategory.text = category.second.name
            binding.cbCategory.isChecked = category.first
        }
    }
}