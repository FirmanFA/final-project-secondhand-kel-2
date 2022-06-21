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
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.home.HomeFragment
import com.binar.secondhand.kel2.ui.login.LoginFragment


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val navController = Navigation.findNavController(requireActivity(), R.id.main_fragment_host)

//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.main_home, R.id.main_account
//            )
//        )

//        val navController = binding.mainFragmentHost.findNavController()
//        setupActionBarWithNavController(
//            requireActivity() as AppCompatActivity,
//            navController,
//            appBarConfiguration
//        )

//        binding.bottomMainFragment.setupWithNavController(navController)


        binding.bottomMainFragment.setOnItemSelectedListener {
            when(it.itemId){

                R.id.main_home -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, HomeFragment())
                        ?.commit()

                    true
                }

                R.id.main_account ->{
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, LoginFragment())
                        ?.commit()

                    true
                }



                else -> false
            }
        }


    }

}