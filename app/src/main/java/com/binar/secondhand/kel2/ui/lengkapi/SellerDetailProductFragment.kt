package com.binar.secondhand.kel2.ui.lengkapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.databinding.FragmentSellerDetailProductBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragment


class SellerDetailProductFragment :
    BaseFragment<FragmentSellerDetailProductBinding>(FragmentSellerDetailProductBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_sell

    }
}