package com.binar.secondhand.kel2.ui.order

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.data.api.model.buyer.order.get.GetOrderResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentMyOrderBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MyOrderFragment : BaseFragment<FragmentMyOrderBinding>(FragmentMyOrderBinding::inflate) {

    private val myOrderViewModel by viewModel<MyOrderViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()
        myOrderViewModel.getMyOrder()


    }

    private fun setUpObserver() {

        myOrderViewModel.myOrderResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.shimmerMyOrder.startShimmer()
                    binding.shimmerMyOrder.visibility = View.VISIBLE
                    binding.rvMyOrder.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.shimmerMyOrder.stopShimmer()
                    binding.shimmerMyOrder.visibility = View.GONE
                    binding.rvMyOrder.visibility = View.VISIBLE
                    if (it.data?.body() != null) {
                        if (it.data.body()?.size == 0) {
                            binding.ivEmpty.visibility = View.VISIBLE
                            binding.tvEmpty.visibility = View.VISIBLE
                            binding.rvMyOrder.visibility = View.GONE
                        } else {
                            binding.ivEmpty.visibility = View.GONE
                            binding.tvEmpty.visibility = View.GONE
                            binding.rvMyOrder.visibility = View.VISIBLE
                            showMyOrder(it.data.body())
                        }
                    }
                }
                Status.ERROR -> {
                    binding.shimmerMyOrder.stopShimmer()
                    binding.shimmerMyOrder.visibility = View.GONE
//                    val error = it.message
//                    Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT)
//                        .show()
                }
            }
        }

    }

    private fun showMyOrder(data: GetOrderResponse?) {
        val adapter = MyOrderAdapter(
            object : MyOrderAdapter.OnClickListener {

                override fun onClickItem(data: GetOrderResponse.GetOrderResponseItem) {
                    val action =
                        MyOrderFragmentDirections
                            .actionMyOrderFragmentToDetailProductFragment(data.productId)
                    findNavController().navigate(action)
                }
            })
        adapter.submitData(data?.sortedByDescending {
            val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'", Locale.ROOT)
            it.transactionDate?.let { it1 -> format.parse(it1) }
        })
        binding.rvMyOrder.adapter = adapter
    }


}