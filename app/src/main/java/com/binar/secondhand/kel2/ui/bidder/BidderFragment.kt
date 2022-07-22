package com.binar.secondhand.kel2.ui.bidder

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.kel2.data.api.model.seller.order.PatchSellerOrderIdRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentBidderBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.bidder.bottomDialog.BidderBerhasilFragment
import com.binar.secondhand.kel2.ui.bidder.bottomDialog.BidderStatusFragment
import com.binar.secondhand.kel2.ui.preview.PreviewFragmentArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.URLEncoder
import java.text.DecimalFormat
import java.text.NumberFormat

class BidderFragment : BaseFragment<FragmentBidderBinding>(FragmentBidderBinding::inflate) {

    private val bidderViewModel: BidderViewModel by viewModel()
    private var orderId: Int? = null
    private var status: String? = null
    private var productId: Int? = null
    private var phoneNumber = ""
    private var productName = ""
    private var bidPrice = ""

    val args: BidderFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
// tambahin args id nya
        val id = args.id


        bidderViewModel.bidder(id)

        loadBidder()

        binding.btnTolak.setOnClickListener {
            status?.let {
                if (it == "pending") {
                    bidderViewModel.statusItem(id, PatchSellerOrderIdRequest(status = "declined"))
                } else {
                    orderId?.let {
                        val modal = BidderStatusFragment(
                            it,
                            productId
                        )
                        modal.show(parentFragmentManager, "Tag")
                    }
                }
            }

        }

        binding.btnTerima.setOnClickListener {
            status?.let { status ->
                if (status == "pending") {
                    orderId?.let {
                        val modal = BidderBerhasilFragment(
                            it
                        )
                        bidderViewModel.statusItem(id, PatchSellerOrderIdRequest(status = "accepted"))
                        modal.show(parentFragmentManager, "Tag")
                    }
                } else {
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
            }

        }


    }

    private fun loadBidder() {
        bidderViewModel.bidder.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    phoneNumber = it.data?.body()?.user?.phoneNumber?:""
                    phoneNumber = if (phoneNumber[0] != '0'){
                        phoneNumber
                    }else{
                        "62${phoneNumber.drop(1)}"
                    }
                    Log.d("phoneNumber", phoneNumber)
                    productName = it.data?.body()?.productName?:""
                    bidPrice = it.data?.body()?.price.toString()
                    val formatter: NumberFormat = DecimalFormat("#,###")
                    val myNumber = it.data?.body()?.basePrice
                    val myBid = it.data?.body()?.price
                    val formattedNumber: String = formatter.format(myNumber).toString()
                    val formattedBid: String = formatter.format(myBid).toString()

                    orderId = it.data?.body()?.id.toString().toInt()
                    productId = it.data?.body()?.productId.toString().toInt()
                    status = it.data?.body()?.status.toString()
                    binding.tvName.text = it.data?.body()?.product?.user?.fullName.toString()
                    binding.tvKota.text = it.data?.body()?.product?.user?.city.toString()
                    Glide.with(this)
                        .load(it.data?.body()?.product?.imageUrl)
                        .centerCrop()
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
                        .into(binding.ivProduct)
                    binding.tvStatus.text = it.data?.body()?.status.toString()
                    binding.tvTitle.text = it.data?.body()?.product?.name.toString()
                    binding.tvPrice.text = "Rp. $formattedNumber"
                    binding.tvNego.text = "Rp. $formattedBid"
                    when(it.data?.body()?.status){
                        "declined" -> {
                            binding.tvNego.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                            binding.btnTerima.visibility = View.GONE
                            binding.btnTolak.visibility = View.GONE
                        }
                        "accepted" ->{
                            binding.tvNego.paintFlags = 0
                            binding.btnTerima.text = "Whatsapp"
                            binding.btnTolak.text = "Status"
                        }

                    }

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Unknown Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        bidderViewModel.status.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    bidderViewModel.bidder(args.id)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Unknown Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}