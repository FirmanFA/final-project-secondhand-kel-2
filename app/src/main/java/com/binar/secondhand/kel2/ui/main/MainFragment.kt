package com.binar.secondhand.kel2.ui.main

import android.os.Bundle
import android.view.View
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.databinding.FragmentMainBinding
import com.binar.secondhand.kel2.ui.account.AccountFragment
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.home.HomeFragment
import com.binar.secondhand.kel2.ui.lengkapi.SellerDetailProductFragment
import com.binar.secondhand.kel2.ui.notification.NotificationFragment
import com.binar.secondhand.kel2.ui.sale.main.ProductSaleListFragment


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    companion object{
        var activePage = 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.bottomMainFragment.setOnItemReselectedListener {  }

        binding.bottomMainFragment.setOnItemSelectedListener {
            when(it.itemId){

                R.id.main_home -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, HomeFragment())
                        ?.commit()

                    true
                }

                R.id.main_notification -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, NotificationFragment())
                        ?.commit()

                    true
                }

                R.id.main_sell ->{
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, SellerDetailProductFragment())
                        ?.commit()

                    true
                }

                R.id.main_sale_list -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, ProductSaleListFragment())
                        ?.commit()

                    true
                }

                R.id.main_account ->{
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, AccountFragment())
                        ?.commit()

                    true
                }

                else -> false
            }
        }

        binding.bottomMainFragment.selectedItemId = if (activePage == 0){
            R.id.main_home
        }else{
            activePage
        }




    }

}