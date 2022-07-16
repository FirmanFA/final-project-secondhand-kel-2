package com.binar.secondhand.kel2.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.databinding.ProductsLoadStateFooterItemBinding

class ProductLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ProductLoadStateAdapter.ReposLoadStateViewHolder>() {

    inner class ReposLoadStateViewHolder(
        private val binding: ProductsLoadStateFooterItemBinding,
        retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ReposLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.products_load_state_footer_item, parent, false)
        val binding = ProductsLoadStateFooterItemBinding.bind(view)
        return ReposLoadStateViewHolder(binding, retry)
    }
}