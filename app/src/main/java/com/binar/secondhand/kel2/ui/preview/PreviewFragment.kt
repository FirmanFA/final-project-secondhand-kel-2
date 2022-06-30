package com.binar.secondhand.kel2.ui.preview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.databinding.FragmentPreviewBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment

class PreviewFragment : BaseFragment<FragmentPreviewBinding>(FragmentPreviewBinding::inflate){

    val args: PreviewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = args.name
        val price = args.price
        val description = args.description
        val location = args.location

        binding.tvTitle.text = name
        binding.tvPrice.text = price
        binding.tvLocation.text = location
        binding.tvDesc.text = description
    }
}