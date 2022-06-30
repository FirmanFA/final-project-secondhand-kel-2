package com.binar.secondhand.kel2.ui.product

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetSellerProductResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentProductSaleListBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductSaleListFragment :
    BaseFragment<FragmentProductSaleListBinding>(FragmentProductSaleListBinding::inflate) {

    private val productSaleListViewModel: ProductSaleListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_sale_list

        val token = getKoin().getProperty("access_token", "")
        if (token==""){
            binding.groupContent.visibility = View.GONE
            binding.groupLogin.visibility = View.VISIBLE
        }else{
            binding.groupLogin.visibility = View.GONE
            setUpObserver()
            productSaleListViewModel.getSellerProduct()
            productSaleListViewModel.getAuth()
        }

        binding.btnLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

        Glide.with(requireContext())
            .load(R.drawable.rectangle_camera)
            .transform(CenterCrop(), RoundedCorners(12))
            .into(binding.ivUserPhoto)



    }

    private fun setUpObserver(){
        productSaleListViewModel.getSellerProductResponse.observe(viewLifecycleOwner){
            when (it.status) {

                Status.LOADING -> {

                }

                Status.SUCCESS -> {


                    when (it.data?.code()) {
                        200 -> {
                            val data = it.data.body()
                            showProduct(data)
                        }

                        else ->{
                            showSnackbar("Error occured: ${it.data?.code()}")
                        }
                    }
                }

                Status.ERROR -> {
                    showSnackbar("${it.message}")
                }
            }
        }

        productSaleListViewModel.authGetResponse.observe(viewLifecycleOwner){
            when(it.status){

                Status.LOADING -> {
                }

                Status.SUCCESS -> {

                    when(it.data?.code()){
                        200 ->{

                            Glide.with(requireContext())
                                .load(it.data.body()?.imageUrl)
                                .transform(CenterCrop(), RoundedCorners(12))
                                .error(R.drawable.rectangle_camera)
                                .into(binding.ivUserPhoto)

                            binding.tvUserName.text = it.data.body()?.fullName
                            binding.tvUserCity.text = it.data.body()?.city
                        }
                    }
                }

                Status.ERROR ->{
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : ${error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showProduct(dataProduct: GetSellerProductResponse?) {
        val adapter = ProductSaleListAdapter{data, position ->

            if (position == 0){
                //TODO:go to add product
            }else{
                //TODO:go to detail product
            }

        }
        if (dataProduct != null) {
            adapter.submitData(dataProduct)
        }
        binding.rvProductSale.adapter = adapter
    }

}