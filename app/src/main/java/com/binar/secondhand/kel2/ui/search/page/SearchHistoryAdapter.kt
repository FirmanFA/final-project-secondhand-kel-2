package com.binar.secondhand.kel2.ui.search.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.kel2.data.local.room.model.SearchHistoryEntity
import com.binar.secondhand.kel2.databinding.SearchHistoryListLayoutBinding

class SearchHistoryAdapter(private val onClick: (SearchHistoryEntity) -> Unit) :
    ListAdapter<SearchHistoryEntity, SearchHistoryAdapter.ViewHolder>(CommunityComparator()) {


    class ViewHolder(private val binding: SearchHistoryListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currentSearchHistoryEntity: SearchHistoryEntity,
            onClick: (SearchHistoryEntity) -> Unit
        ) {
            binding.root.setOnClickListener {
                onClick(currentSearchHistoryEntity)
            }
            binding.tvSearch.text = currentSearchHistoryEntity.searchKeyword

        }

    }

    class CommunityComparator : DiffUtil.ItemCallback<SearchHistoryEntity>() {
        override fun areItemsTheSame(
            oldItem: SearchHistoryEntity,
            newItem: SearchHistoryEntity
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: SearchHistoryEntity,
            newItem: SearchHistoryEntity
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchHistoryListLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

}