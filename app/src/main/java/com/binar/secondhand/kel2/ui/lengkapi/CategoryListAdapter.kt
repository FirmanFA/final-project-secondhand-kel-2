package com.binar.secondhand.kel2.ui.lengkapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponseItem
import com.binar.secondhand.kel2.databinding.SelectCategoryListLayoutBinding

class CategoryListAdapter(
    private val onClick: (GetCategoryResponseItem, Boolean, Int) -> Unit
) :
    BaseAdapter() {

    private val listCategory =  ArrayList<Pair<Boolean, GetCategoryResponseItem>>()

    fun submitData(submitListCategory: List<Pair<Boolean, GetCategoryResponseItem>>){
        listCategory.addAll(submitListCategory)
    }

    override fun getCount() = listCategory.size

    override fun getItem(p0: Int) = listCategory[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getView(p0: Int, convertView: View?, p2: ViewGroup?): View {
        val binding: SelectCategoryListLayoutBinding = if (convertView == null) {
            SelectCategoryListLayoutBinding
                .inflate(LayoutInflater.from(p2?.context), p2, false)
        } else {
            SelectCategoryListLayoutBinding.bind(convertView)
        }

        val category = getItem(p0)

        binding.cbCategory.setOnCheckedChangeListener { _, isChecked ->
            onClick(category.second, isChecked, p0)
        }
        binding.cbCategory.text = category.second.name
        binding.cbCategory.isChecked = category.first

        return binding.root
    }
}