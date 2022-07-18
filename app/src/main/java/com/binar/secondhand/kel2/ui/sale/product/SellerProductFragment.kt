package com.binar.secondhand.kel2.ui.sale.product

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetSellerProductResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentSellerProductBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
import com.binar.secondhand.kel2.ui.main.MainFragmentDirections
import com.binar.secondhand.kel2.ui.sale.main.ProductSaleListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SellerProductFragment :
    BaseFragment<FragmentSellerProductBinding>(FragmentSellerProductBinding::inflate) {

    private val productSaleListViewModel by sharedViewModel<ProductSaleListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()
        productSaleListViewModel.getSellerProduct()


    }

    private fun setUpObserver(){
        productSaleListViewModel.getSellerProductResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                    binding.productShimmer.startShimmer()
                }

                Status.SUCCESS -> {

                    binding.productShimmer.stopShimmer()
                    binding.productShimmer.visibility = View.GONE

                    when (it.data?.code()) {
                        200 -> {
                            val data = it.data.body()
                            showProduct(data)
                        }

                        else -> {
                            showSnackbar("Internal server error")
                        }
                    }
                }

                Status.ERROR -> {
                    showSnackbar("Internal server error")
                }
            }
        }
    }

    private fun showProduct(dataProduct: GetSellerProductResponse?) {
        val adapter = SellerProductAdapter ({ data, position ->
            if (position == 0) {
                MainFragment.activePage = R.id.main_sell
                findNavController().navigate(R.id.action_mainFragment_self)
            } else {
                val action =
                    MainFragmentDirections.actionMainFragmentToDetailProductFragment(data.id)
                findNavController().navigate(action)
            }
        },{
          productSaleListViewModel.deleteProduct(it)
            findNavController().navigate(R.id.action_mainFragment_self)
        },{_ , id ->
            val actionToEditFragment = MainFragmentDirections.actionMainFragmentToEditFragment(id)
            findNavController().navigate(actionToEditFragment)
        })
        if (dataProduct != null) {
            adapter.submitData(dataProduct)
        }
        binding.rvSellerProduct.adapter = adapter
    }

}