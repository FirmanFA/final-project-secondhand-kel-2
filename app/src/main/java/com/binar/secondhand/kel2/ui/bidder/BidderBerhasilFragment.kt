package com.binar.secondhand.kel2.ui.bidder

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.net.Uri
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
import java.text.DecimalFormat
import java.text.NumberFormat

class BidderBerhasilFragment (
    orderId: Int,
//    private val refreshButton: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentBidderBerhasilBinding? = null
    private val binding get() = _binding!!
    private val orderId = orderId
    private val viewModel: BidderBerhasilViewModel by viewModel()
    private var phone: String? = null


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

        binding.btnKirim.setOnClickListener {
            directToWa()
        }

    }

    private fun directToWa() {
        if (isWhatappInstalled()) {
            val i = Intent(
                Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + "$phone" + "&text=" + "")
            )
            startActivity(i)
        } else {
            Toast.makeText(requireContext(), "Whatsapp is not installed", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun isWhatappInstalled(): Boolean {
        val packageManager = requireContext().packageManager
        val whatsappInstalled: Boolean
        whatsappInstalled = try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return whatsappInstalled
    }

    private fun setUpObserver() {
        viewModel.bidderProduct.observe(viewLifecycleOwner){
             when(it.status){
                 Status.LOADING -> {
                     binding.progressBar.visibility = View.VISIBLE
                 }
                 Status.SUCCESS -> {
                     phone = it.data?.body()?.User?.phone_number.toString()
                     val price = it.data?.body()?.Product?.base_price.toString()
                     val ditawar = it.data?.body()?.price.toString()
                     val formatter: NumberFormat = DecimalFormat("#,###")

                     val myPrice = price.toInt()
                     val formattedPrice: String = formatter.format(myPrice).toString()

                     val myDitawar = ditawar.toInt()
                     val formattedDitawar: String = formatter.format(myDitawar).toString()

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
                         tvName.text = it.data?.body()?.User?.full_name
                         tvCity.text = it.data?.body()?.User?.city
                         tvNameProduct.text = it.data?.body()?.Product?.name
                         binding.tvPrice.text = "Rp. $formattedPrice"
                         val price = tvPrice.text.toString().replace("Rp. ", "").replace(".", "")
                         tvPrice.text = "Rp. $price"


                         binding.tvDitawar.text = "Rp. $formattedDitawar"
                         val ditawar = tvDitawar.text.toString().replace("Rp. ", "").replace(".", "")
                         tvDitawar.text = "Ditawar Rp. $ditawar"
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