package com.binar.secondhand.kel2.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.databinding.FragmentProductSaleListBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


class ProductSaleListFragment :
    BaseFragment<FragmentProductSaleListBinding>(FragmentProductSaleListBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_sale_list

        Glide.with(requireContext())
            .load(R.drawable.rectangle_camera)
            .transform(CenterCrop(), RoundedCorners(12))
            .into(binding.ivUserPhoto)
        showProduct()


    }

    private fun showProduct(){
        val adapter = ProductSaleListAdapter{data, position ->

            if (position == 0){
                //TODO:go to add product
            }else{
                //TODO:go to detail product
            }

        }
        binding.rvProductSale.adapter = adapter
    }

}