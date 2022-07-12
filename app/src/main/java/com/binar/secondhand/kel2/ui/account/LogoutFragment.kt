package com.binar.secondhand.kel2.ui.account

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponseItem
import com.binar.secondhand.kel2.databinding.FragmentAccountBinding
import com.binar.secondhand.kel2.databinding.FragmentLogoutBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.login.LoginFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
import org.koin.android.ext.android.getKoin

class LogoutFragment(private val onLogout: () -> Unit) : DialogFragment() {
    private var _binding : FragmentLogoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_radius)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_account

//        preferences =
//            this.requireActivity().getSharedPreferences(LoginFragment.LOGINUSER, Context.MODE_PRIVATE)

        binding.tvKembali.setOnClickListener {
            dialog?.dismiss()
        }

        binding.tvYa.setOnClickListener {
            onLogout.invoke()
//            if (findNavController().currentDestination?.id == R.id.logoutFragment) {
//                findNavController().navigate(R.id.action_logoutFragment_to_mainFragment)
//            }
            dialog?.dismiss()

// Still error menggunakan findnav controler
//            findNavController().navigate(R.id.action_logoutFragment_to_accountFragment)
//            preferences.edit().clear().apply()
//            getKoin().setProperty("access_token","")
//            findNavController().navigate(R.id.action_logoutFragment_to_mainFragment)

//            findNavController().navigate(R.id.action_logoutFragment_to_mainFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}