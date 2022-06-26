package com.binar.secondhand.kel2.ui.notification

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentNotificationBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
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

        MainFragment.activePage = R.id.main_notification

//        val preferences = this.activity?.getSharedPreferences(LoginFragment.LOGINUSER, Context.MODE_PRIVATE)
//        val email = preferences?.getString(LoginFragment.EMAIL,"")
        val token = getKoin().getProperty("access_token","")

        if (token == ""){
            binding.rvNotification.visibility = View.GONE

            Log.d("list", "token kosong")

            binding.btnLogin.setOnClickListener{
                it.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            }

        }else{
            Log.d("list", "token tidak kosong")
            binding.ivLogin.visibility = View.GONE
            binding.tvLogin.visibility = View.GONE
            binding.btnLogin.visibility = View.GONE
            binding.rvNotification.visibility = View.VISIBLE
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
                        list = it.data.body()!!
                        listSize = it.data.body()!!.size
                        it.data.body()?.forEach {notification->
                            notificationViewModel.getSellerProductId(notification.productId)
                        }
                    }
                }
                Status.ERROR -> {
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT).show()
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
                        listProduct.add(GetProductIdResponse(0, listOf(),"-",0,"-","-","-","-","-",0,""))
                    }
                    if (list.size != 0){
                        Log.d("List",list.size.toString())
                        Log.d("List product",listProduct.size.toString())
                        if (listProduct.size == list.size){
                            Log.d("list", "notification: masuk if")
                            Log.d("list", listProduct.toString())
                            Log.d("list", list.toString())
                            val adapter = NotificationAdapter(object : NotificationAdapter.OnClickListener{
                                override fun onClickItem(data: GetNotificationResponse.GetNotificationResponseItem) {
                                    val id = data.id
                                    findNavController().navigate(R.id.action_mainFragment_to_bidderFragment)
                                }
                            },listProduct
                            )
                            adapter.submitData(list)
                            binding.rvNotification.adapter = adapter
                            Log.d("list", "adapter count: ${adapter.itemCount}")
                        }
                    }
                }
                else -> {}
            }
        }
    }
}