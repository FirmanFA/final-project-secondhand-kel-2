package com.binar.secondhand.kel2.ui.bidder

import android.graphics.Paint
import android.os.Bundle
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

class BidderFragment : BaseFragment<FragmentBidderBinding>(FragmentBidderBinding::inflate) {

    private val bidderViewModel: BidderViewModel by viewModel()
    private var orderId: Int? = null
    private var status: String? = null

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
                            it
                        )
                        modal.show(parentFragmentManager, "Tag")
                    }
                }
            }

        }

        binding.btnTerima.setOnClickListener {
            orderId?.let {
                val modal = BidderBerhasilFragment(
                    it
                )
                bidderViewModel.statusItem(id, PatchSellerOrderIdRequest(status = "accepted"))
                modal.show(parentFragmentManager, "Tag")
            }
        }


    }

    private fun loadBidder() {
        bidderViewModel.bidder.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    orderId = it.data?.body()?.id.toString().toInt()
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
                    binding.tvPrice.text = it.data?.body()?.basePrice.toString()
                    binding.tvNego.text = it.data?.body()?.price.toString()
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
                    bidderViewModel.bidder(args.id)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Unknown Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}