package com.binar.secondhand.kel2.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.databinding.FragmentOnBoardingBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class OnBoardingFragment :
    BaseFragment<FragmentOnBoardingBinding>(FragmentOnBoardingBinding::inflate) {

    var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager : ViewPager? = null
    var next: FloatingActionButton? = null
    var position = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = binding.tabIndicator
        next = binding.fab

        val onBoardingData:MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData("Temukan Barang Bekas Di Tempat Ini", R.drawable.onboarding_1))
        onBoardingData.add(OnBoardingData("Cara Sempurna Untuk Transaksi Barang Bekas Di Platform Ini", R.drawable.onboarding_2))
        onBoardingData.add(OnBoardingData("Semua Yang Anda Butuhkan, Hanya Ada Di Sini", R.drawable.onboarding_3))

        setOnBoardingViewPagerAdapter(onBoardingData)

        position = onBoardingViewPager!!.currentItem

        next?.setOnClickListener {
            if (position < onBoardingData.size){
                position++
                onBoardingViewPager!!.currentItem = position
            }
            if (position == onBoardingData.size){
                //pindah fragment
                findNavController().navigate(R.id.action_onBoardingFragment_to_mainFragment)
            }
        }
        binding.vpOnboarding.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageSelected(post: Int) {
                position = post
            }
        })
    }

    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>){
        onBoardingViewPager = binding.vpOnboarding
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(requireContext(), onBoardingData)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
        tabLayout?.setupWithViewPager(onBoardingViewPager)
    }

}