package com.binar.secondhand.kel2.ui.sale.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.binar.secondhand.kel2.ui.sale.bid.BidProductFragment
import com.binar.secondhand.kel2.ui.sale.product.SellerProductFragment

class ProductViewPagerAdapter(private val fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                SellerProductFragment()
            }
            1 -> {
                BidProductFragment()
            }
            else ->{
                SellerProductFragment()
            }
        }
    }

}