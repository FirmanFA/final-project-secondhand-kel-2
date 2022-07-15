package com.binar.secondhand.kel2.ui.bidder

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentBidderBerhasilBinding
import com.binar.secondhand.kel2.databinding.FragmentBuyerPenawaranBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class BidderBerhasilFragment (
    orderId: Int,
//    private val refreshButton: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentBidderBerhasilBinding? = null
    private val binding get() = _binding!!
    private val orderId = orderId
    private val viewModel: BidderBerhasilViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBidderBerhasilBinding.inflate(layoutInflater)
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
            viewModel.getOrderProductId(orderId)
            setUpObserver()
        }

    }

    private fun setUpObserver() {
        viewModel.bidderProduct.observe(viewLifecycleOwner){
             when(it.status){
                 Status.LOADING -> {
                     binding.progressBar.visibility = View.VISIBLE
                 }
                 Status.SUCCESS -> {
                     binding.tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                     binding.progressBar.visibility = View.GONE
                     //sukses mendapat response, progressbar disembunyikan lagi
                     Glide.with(binding.imgProfile)
                         .load(it.data?.body()?.image_product)
                         .error(R.drawable.ic_broken)
                         .into(binding.imgProfile)

                     Glide.with(binding.imgProduct)
                         .load(it.data?.body()?.Product?.image_url)
                         .error(R.drawable.ic_broken)
                         .into(binding.imgProduct)

                     binding.apply {
                         it.data?.body()?.User?.full_name
                         it.data?.body()?.User?.city
                         it.data?.body()?.Product?.name
                         it.data?.body()?.Product?.base_price
                         it.data?.body()?.price
                     }
                 }
                 Status.ERROR ->{
                     binding.progressBar.visibility = View.GONE
                     val error = it.message
                     Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT).show()
                 }
             }
        }
    }

}