package com.binar.secondhand.kel2.ui.sale.sold

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.data.api.model.seller.order.GetOrderResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentSoldProductBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragmentDirections
import com.binar.secondhand.kel2.ui.sale.bid.BidProductAdapter
import com.binar.secondhand.kel2.ui.sale.main.ProductSaleListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SoldProductFragment :
    BaseFragment<FragmentSoldProductBinding>(FragmentSoldProductBinding::inflate) {

    private val productSaleListViewModel by sharedViewModel<ProductSaleListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()
        productSaleListViewModel.getNotification()

    }

    private fun setUpObserver(){

        productSaleListViewModel.notificationResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.shimmerNotification.startShimmer()
                    binding.shimmerNotification.visibility = View.VISIBLE
                    binding.rvNotification.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.shimmerNotification.stopShimmer()
                    binding.shimmerNotification.visibility = View.GONE
                    binding.rvNotification.visibility = View.VISIBLE
                    if (it.data?.body() != null) {
                        if (it.data.body()?.size == 0) {
                            binding.ivEmpty.visibility = View.VISIBLE
                            binding.tvEmpty.visibility = View.VISIBLE
                            binding.rvNotification.visibility = View.GONE
                        } else {
                            binding.ivEmpty.visibility = View.GONE
                            binding.tvEmpty.visibility = View.GONE
                            binding.rvNotification.visibility = View.VISIBLE
                            showBidProduct(it.data.body())
                        }
                    }
                }
                Status.ERROR -> {
                    binding.shimmerNotification.stopShimmer()
                    binding.shimmerNotification.visibility = View.GONE
                    val error = it.message
//                    Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT)
//                        .show()
                }
            }
        }

    }

    private fun showBidProduct(data: GetOrderResponse?) {
        val filteredData = data?.filter {
            it.status == "accepted"
        }
        val adapter = BidProductAdapter(
            object : BidProductAdapter.OnClickListener {

                override fun onClickItem(data: GetOrderResponse.GetOrderResponseItem) {
                    val id = data.id
                    val action = MainFragmentDirections.actionMainFragmentToBidderFragment(
                        id
                    )
                    findNavController().navigate(action)
                }
            })
        adapter.submitData(filteredData)
        binding.rvNotification.adapter = adapter
    }
}