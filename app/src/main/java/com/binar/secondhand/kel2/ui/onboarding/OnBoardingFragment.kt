package com.binar.secondhand.kel2.ui.onboarding

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.databinding.FragmentOnBoardingBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout

class OnBoardingFragment :
    BaseFragment<FragmentOnBoardingBinding>(FragmentOnBoardingBinding::inflate) {

    var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager : ViewPager? = null
    var next: TextView? = null
    var position = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = binding.tabIndicator
        next = binding.tvNext

        val onBoardingData:MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData("Barang Bekas","Temukan Barang Bekas Di Tempat Ini", R.drawable.ic_baseline_shopping_cart_36))
        onBoardingData.add(OnBoardingData("Semuanya Jenis Ada","Semua Yang Anda Butuhkan, Hanya Ada Di Sini", R.drawable.ic_baseline_shopping_cart_36))
        onBoardingData.add(OnBoardingData("Sempurna","Cara Sempurna Untuk Transaksi Barang Bekas Di Platform Ini", R.drawable.ic_baseline_shopping_cart_36))

        setOnBoardingViewPagerAdapter(onBoardingData)

        position = onBoardingViewPager!!.currentItem

        next?.setOnClickListener {
            if (position < onBoardingData.size){
                position++
                onBoardingViewPager!!.currentItem = position
            }
            if (position == onBoardingData.size){
                //pindah fragment
            }
        }
    }

    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>){
        onBoardingViewPager = binding.vpOnboarding
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(requireContext(), onBoardingData)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
        tabLayout?.setupWithViewPager(onBoardingViewPager)
    }

}