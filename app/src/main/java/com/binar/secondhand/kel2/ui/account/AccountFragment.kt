package com.binar.secondhand.kel2.ui.account

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.databinding.FragmentAccountBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.login.LoginFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
import org.koin.android.ext.android.getKoin

class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {

    private lateinit var preferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_account

        preferences =
            this.requireActivity().getSharedPreferences(LoginFragment.LOGINUSER, Context.MODE_PRIVATE)

        binding.containerUbahAkun.setOnClickListener {
            // Ke fragment ubah akun

            val token = preferences.getString(LoginFragment.TOKEN, "")

            if (token == ""){
                it.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            }else{
                it.findNavController().navigate(R.id.action_mainFragment_to_profileFragment2)
            }


        }

        binding.containerPengaturanAkun.setOnClickListener {
            // Ke fragment pengaturan akun
            Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show()
        }

        binding.containerKeluar.setOnClickListener {
            // Logout system
            AlertDialog.Builder(requireContext())
                .setTitle("Keluar")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dialog.dismiss()
                    preferences.edit().clear().apply()
                    getKoin().setProperty("access_token","")
                    // Ke Fragment login

                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

    }

}