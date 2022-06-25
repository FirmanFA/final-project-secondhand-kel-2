package com.binar.secondhand.kel2.ui.notification

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.id.get.GetProductIdResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentNotificationBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.login.LoginFragment
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::inflate) {

    private val notificationViewModel: NotificationViewModel by viewModel()

    private var list = GetNotificationResponse()

    private val listProduct = ArrayList<GetProductIdResponse>()

    private var listSize = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = this.activity?.getSharedPreferences(LoginFragment.LOGINUSER, Context.MODE_PRIVATE)
        val email = preferences?.getString(LoginFragment.EMAIL,"")
        val token = getKoin().getProperty("access_token","")

        if (token == ""){
            binding.rvNotification.visibility = View.GONE
        }else{
            binding.ivLogin.visibility = View.GONE
            binding.tvLogin.visibility = View.GONE
            notification()
            notificationViewModel.getNotification()
        }
    }

    private fun notification() {
        notificationViewModel.notificationResponse.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    if (it.data?.body() != null){
                        list = it.data?.body()!!
                        listSize = it.data.body()!!.size
                        it.data?.body()?.forEach {
                            notificationViewModel.getSellerProductId(it.productId)
                        }
                    }
                }
                Status.ERROR -> {
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : ${error}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        notificationViewModel.sellerProductIdResponse.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    if (it.data?.body() != null){
                        listProduct.add(it.data.body()!!)
                    }else{
                        listProduct.add(GetProductIdResponse(0, listOf(),"-",0,"-","-","-","-","-",0))
                    }
                    if (list.size != 0){
                        Log.d("List",list.size.toString())
                        Log.d("List product",listProduct.size.toString())
                        if (listProduct.size == list.size){
                            val adapter = NotificationAdapter(object : NotificationAdapter.OnClickListener{
                                override fun onClickItem(data: GetNotificationResponse.GetNotificationResponseItem) {
                                    val id = data.id
                                }
                            },listProduct
                            )
                            adapter.submitData(list)
                            binding.rvNotification.adapter = adapter
                        }
                    }
                }
            }
        }
    }
}