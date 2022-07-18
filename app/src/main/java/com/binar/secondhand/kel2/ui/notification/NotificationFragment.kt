package com.binar.secondhand.kel2.ui.notification

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentNotificationBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
import com.binar.secondhand.kel2.ui.main.MainFragmentDirections
import com.binar.secondhand.kel2.ui.main.MainViewModel
import com.binar.secondhand.kel2.ui.sale.bid.BidProductAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::inflate) {

    private val notificationViewModel: NotificationViewModel by viewModel()
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_notification

        val token = getKoin().getProperty("access_token", "")

        if (token == "") {
            binding.shimmerNotification.stopShimmer()
            binding.shimmerNotification.visibility = View.GONE
            binding.rvNotification.visibility = View.GONE
            binding.tvNotification.visibility = View.GONE
            binding.tvFilter.visibility = View.GONE
            binding.ivFilter.visibility = View.GONE

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

        binding.ivFilter.setOnClickListener {
            //do filter
            val popupMenu = PopupMenu(requireContext(), binding.ivFilter)
            popupMenu.menuInflater.inflate(R.menu.notification_filter_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.notification_all -> {
                        notificationViewModel.getNotification()
                        binding.tvFilter.text = "All Notification"
                    }
                    R.id.notification_buyer -> {
                        notificationViewModel.getNotification("buyer")
                        binding.tvFilter.text = "Buyer Notification"
                    }
                    R.id.notification_seller -> {
                        notificationViewModel.getNotification("seller")
                        binding.tvFilter.text = "Seller Notification"
                    }
                }
                true
            }
            popupMenu.show()
        }

    }

    private fun setUpObserver() {

        notificationViewModel.notificationResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.shimmerNotification.startShimmer()
                    binding.shimmerNotification.visibility = View.VISIBLE
                    binding.rvNotification.visibility = View.GONE
                    binding.ivLogin.visibility = View.GONE
                    binding.tvLogin.visibility = View.GONE
                    binding.btnLogin.visibility = View.GONE
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
                            binding.ivLogin.visibility = View.GONE
                            binding.tvLogin.visibility = View.GONE
                            binding.btnLogin.visibility = View.GONE
                            showBidProduct(it.data.body())
                        }
                        val unreadCount = it.data.body()?.filter { notificationResponseItem ->
                            !notificationResponseItem.read
                        }
                        mainViewModel.setNotificationCount(unreadCount?.size)
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
        notificationViewModel.readNotificationResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {}
                Status.ERROR -> {
                    showSnackbar("Error Occured")
                }
                Status.LOADING -> {}
            }
        }

    }

    private fun showBidProduct(data: GetNotificationResponse?) {
        val adapter = NotificationAdapter(
            object : NotificationAdapter.OnClickListener {
                override fun onClickItem(
                    data: GetNotificationResponse.GetNotificationResponseItem,
                ) {

                    notificationViewModel.readNotification(data.id)

                    if (data.product != null) {
                        val id = data.productId
                        val action =
                            MainFragmentDirections.actionMainFragmentToDetailProductFragment(id)
                        findNavController().navigate(action)
                    } else {
                        val snackbar =
                            Snackbar.make(
                                binding.snackbar,
                                "Product Ini Sudah Tidak Tersedia!",
                                Snackbar.LENGTH_LONG
                            )
                        snackbar.setAction("x") {
                            // Responds to click on the action
                            snackbar.dismiss()
                        }
                            .setBackgroundTint(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red
                                )
                            )
                            .setActionTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                            .show()
                    }

                }
            })

        adapter.submitData(data?.sortedByDescending {
            val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'", Locale.ROOT)
            format.parse(it.createdAt)
        })
        binding.rvNotification.adapter = adapter
    }
}