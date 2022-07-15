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
import com.binar.secondhand.kel2.ui.preview.PreviewFragmentArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class BidderFragment : BaseFragment<FragmentBidderBinding>(FragmentBidderBinding::inflate) {

    private val bidderViewModel: BidderViewModel by viewModel()

    val args: BidderFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
// tambahin args id nya
        val id = args.id


        bidderViewModel.bidder(id)

        loadBidder()

        binding.btnTolak.setOnClickListener {
            bidderViewModel.statusItem(id, PatchSellerOrderIdRequest(status = "declined"))
        }


    }

    private fun loadBidder() {
        bidderViewModel.bidder.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    var orderId = it.data?.body()?.id
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

                        }

                    }
                    binding.btnTerima.setOnClickListener {
                        val modal = BidderBerhasilFragment(
                            id
                        )
                        modal.show(parentFragmentManager, "Tag")
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