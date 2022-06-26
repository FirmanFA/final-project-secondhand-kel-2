package com.binar.secondhand.kel2.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.databinding.FragmentMainBinding
import com.binar.secondhand.kel2.ui.account.AccountFragment
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.home.HomeFragment
import com.binar.secondhand.kel2.ui.lengkapi.SellerDetailProductFragment
import com.binar.secondhand.kel2.ui.login.LoginFragment
import com.binar.secondhand.kel2.ui.notification.NotificationFragment


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