package com.binar.secondhand.kel2.ui.account

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.binar.secondhand.kel2.databinding.FragmentAccountBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment

class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {

    private lateinit var preferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.containerUbahAkun.setOnClickListener {
            // Ke fragment ubah akun
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
                    // Ke Fragment login

                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

    }

}