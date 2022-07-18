package com.binar.secondhand.kel2.ui.lengkapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponseItem
import com.binar.secondhand.kel2.databinding.BottomDialogCategoryBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*

class CategoryBottomDialog(
    private val listCategory: ArrayList<Pair<Boolean, GetCategoryResponseItem>>,
    private val onSave: (ArrayList<GetCategoryResponseItem>) -> Unit
) : BottomSheetDialogFragment() {

//    private lateinit var binding:

    private lateinit var binding: BottomDialogCategoryBinding
    private val listSelectedCategory = ArrayList<GetCategoryResponseItem>()
    private lateinit var adapter: SelectCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomDialogCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun ChipGroup.addChip(category: GetCategoryResponseItem, chipId: Int) {

        Chip(context).apply {
            id = chipId
            text = category.name
            isClickable = true
            isCloseIconVisible = true
            setOnCloseIconClickListener {
                listCategory.add(chipId, Pair(false, category))
                adapter.notifyItemChanged(chipId)
                removeView(it)
            }
            addView(this)

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipGroup = binding.chipGroupSelectedCategory



        adapter = SelectCategoryAdapter { category, isChecked, pos ->
            if (isChecked) {
                listSelectedCategory.add(category)
                chipGroup.addChip(category, pos)
            } else {
                listSelectedCategory.remove(category)
                val chip = view.findViewById<Chip>(pos)
                chipGroup.removeView(chip)
            }
        }
        adapter.submitData(listCategory)
        binding.rvSelectCategory.adapter = adapter

        binding.btnSave.setOnClickListener {
            onSave(listSelectedCategory)
        }

        binding.etSearch.editText?.addTextChangedListener { keyword ->
            val filteredList =
                listCategory.filter {
                    it.second.name.lowercase().contains(keyword.toString().lowercase())
                }

            adapter.submitData(filteredList)
        }

    }


}