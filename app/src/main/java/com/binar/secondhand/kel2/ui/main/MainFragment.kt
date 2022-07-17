package com.binar.secondhand.kel2.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.databinding.FragmentMainBinding
import com.binar.secondhand.kel2.ui.account.AccountFragment
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.home.HomeFragment
import com.binar.secondhand.kel2.ui.lengkapi.SellerDetailProductFragment
import com.binar.secondhand.kel2.ui.login.LoginFragment
import com.binar.secondhand.kel2.ui.notification.NotificationFragment
import com.binar.secondhand.kel2.ui.sale.main.ProductSaleListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val mainViewModel by activityViewModels<MainViewModel>()

    companion object {
        var activePage = 0
        var statusTerbit = ""
    }

    private lateinit var preferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.bottomMainFragment.setOnItemReselectedListener {
//            if (activePage == 0){
//                activity?.supportFragmentManager?.beginTransaction()
//                    ?.replace(R.id.main_fragment_host, HomeFragment())
//                    ?.commit()
//            }
//        }

        preferences =
            this.requireActivity()
                .getSharedPreferences("notification", Context.MODE_PRIVATE)

        val latestUnreadCount = preferences.getInt("unread_count",0)

        val notificationBadge =
            binding.bottomMainFragment.getOrCreateBadge(R.id.main_notification)

        if (latestUnreadCount == 0) {
            notificationBadge.clearNumber()
            notificationBadge.isVisible = false
        } else {
            notificationBadge.isVisible = true
            notificationBadge.number = latestUnreadCount
        }

        setupObserver()

        binding.bottomMainFragment.setOnItemSelectedListener {
            when (it.itemId) {

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

                R.id.main_sell -> {
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

                R.id.main_account -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, AccountFragment())
                        ?.commit()

                    true
                }

                else -> false
            }
        }

        binding.bottomMainFragment.selectedItemId = if (activePage == 0) {
            R.id.main_home
        } else {
            activePage
        }


    }

    private fun setupObserver() {
        mainViewModel.notificationCount.observe(viewLifecycleOwner) {

            val notificationBadge =
                binding.bottomMainFragment.getOrCreateBadge(R.id.main_notification)

            if (it == 0) {
                notificationBadge.clearNumber()
                notificationBadge.isVisible = false
                preferences.edit().clear().apply()
            } else {
                notificationBadge.isVisible = true
                if (it != null) {
                    notificationBadge.number = it
                    preferences.edit().putInt("unread_count", it).apply()
                }
            }
        }
    }

}