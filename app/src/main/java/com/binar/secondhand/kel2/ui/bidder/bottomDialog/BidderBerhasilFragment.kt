package com.binar.secondhand.kel2.ui.bidder.bottomDialog

import android.content.Intent
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
import com.binar.secondhand.kel2.ui.bidder.BidderViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.URLEncoder
import java.text.DecimalFormat
import java.text.NumberFormat


class BidderBerhasilFragment(
    orderId: Int,
//    private val refreshButton: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentBidderBerhasilBinding? = null
    private val binding get() = _binding!!
    private val orderId = orderId
    private val viewModel: BidderViewModel by viewModel()
    private var phoneNumber = ""
    private var productName = ""
    private var bidPrice = ""


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

            binding.btnKirim.setOnClickListener {
                try {
                    val packageManager = requireActivity().packageManager
                    val i = Intent(Intent.ACTION_VIEW)
                    val message = "Saya tertarik dengan tawaran anda untuk: $productName\n" +
                            "dengan harga: Rp $bidPrice"
                    val url =
                        "https://api.whatsapp.com/send?phone=$phoneNumber&text=" + URLEncoder.encode(
                            message,
                            "UTF-8"
                        )
                    i.setPackage("com.whatsapp")
                    i.data = Uri.parse(url)
                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i)
                    } else {
                        Toast.makeText(
                            context,
                            "Anda belum menginstall whatsapp",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Anda belum menginstall whatsapp",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            viewModel.getOrderProductId(orderId)
            setUpObserver()
        }

    }

    private fun setUpObserver() {
        viewModel.bidderProduct.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    phoneNumber = it.data?.body()?.User?.phone_number?:""
                    phoneNumber = if (phoneNumber[0] == '0'){
                        "62"+phoneNumber.drop(1)
                    }else{
                        phoneNumber
                    }
                    productName = it.data?.body()?.product_name?:""
                    bidPrice = it.data?.body()?.price.toString()
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
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}