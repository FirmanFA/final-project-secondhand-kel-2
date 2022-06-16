package com.binar.secondhand.kel2.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.databinding.FragmentOnBoardingBinding
import com.binar.secondhand.kel2.databinding.FragmentProfileBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val city = resources.getStringArray(R.array.city)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.list_item, city)

        binding.autoCompleteTv.setAdapter(arrayAdapter)
    }
}