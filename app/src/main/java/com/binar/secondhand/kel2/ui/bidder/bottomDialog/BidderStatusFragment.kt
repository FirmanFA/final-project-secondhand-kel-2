package com.binar.secondhand.kel2.ui.bidder.bottomDialog

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentBidderBerhasilBinding
import com.binar.secondhand.kel2.databinding.FragmentBidderStatusBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.text.NumberFormat


class BidderStatusFragment (
    orderId: Int,
//    private val refreshButton: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentBidderStatusBinding? = null
    private val binding get() = _binding!!
    private val orderId = orderId
    private val viewModel: BidderStatusViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBidderStatusBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var token = getKoin().getProperty("access_token", "")

        if (token == "") {

            binding.dialogLogin.visibility = View.VISIBLE
            binding.dialogBottom.visibility = View.GONE
            binding.btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_detailProductFragment_to_loginFragment)
                dismiss()
            }

        } else {
            binding.dialogLogin.visibility = View.GONE
            binding.dialogBottom.visibility = View.VISIBLE

            setUpObserver()
        }

    }

    private fun setUpObserver() {
//
    }

}