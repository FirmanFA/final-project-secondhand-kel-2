package com.binar.secondhand.kel2.ui.notification

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentNotificationBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
import com.binar.secondhand.kel2.ui.sale.bid.BidProductAdapter
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::inflate) {

    private val notificationViewModel: NotificationViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_notification

        val token = getKoin().getProperty("access_token", "")

        if (token == "") {
            binding.shimmerNotification.stopShimmer()
            binding.shimmerNotification.visibility = View.GONE
            binding.rvNotification.visibility = View.GONE
            binding.tvNotification.visibility = View.GONE

            binding.btnLogin.setOnClickListener {
                it.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            }

        } else {
            binding.ivLogin.visibility = View.GONE
            binding.tvLogin.visibility = View.GONE
            binding.btnLogin.visibility = View.GONE
            binding.rvNotification.visibility = View.VISIBLE
            binding.shimmerNotification.visibility = View.GONE
            setUpObserver()
            notificationViewModel.getNotification()
        }

    }

    private fun setUpObserver(){

        notificationViewModel.notificationResponse.observe(viewLifecycleOwner) {
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
                            binding.tvLogin.text = "Tidak ada notifikasi"
                            binding.ivLogin.visibility = View.VISIBLE
                            binding.tvLogin.visibility = View.VISIBLE
                            binding.rvNotification.visibility = View.GONE
                        } else {
                            showBidProduct(it.data.body())
                        }
                    }
                }
                Status.ERROR -> {
                    binding.shimmerNotification.stopShimmer()
                    binding.shimmerNotification.visibility = View.GONE
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    private fun showBidProduct(data: GetNotificationResponse?) {
        val adapter = NotificationAdapter(
            object : NotificationAdapter.OnClickListener {
                override fun onClickItem(data: GetNotificationResponse.GetNotificationResponseItem) {
                    val id = data.id
                    findNavController().navigate(R.id.action_mainFragment_to_bidderFragment)
                }
            })

        adapter.submitData(data?.sortedByDescending {
            val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'", Locale.ROOT)
            format.parse(it.createdAt)
        })
        binding.rvNotification.adapter = adapter
    }
}