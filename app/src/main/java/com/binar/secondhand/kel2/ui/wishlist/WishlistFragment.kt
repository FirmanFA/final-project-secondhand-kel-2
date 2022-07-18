package com.binar.secondhand.kel2.ui.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.wishlist.get.GetWishlist
import com.binar.secondhand.kel2.data.api.model.wishlist.get.GetWishlistItem
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentNotificationBinding
import com.binar.secondhand.kel2.databinding.FragmentWishlistBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragmentDirections
import com.binar.secondhand.kel2.ui.notification.NotificationAdapter
import com.binar.secondhand.kel2.ui.notification.NotificationViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class WishlistFragment :
    BaseFragment<FragmentWishlistBinding>(FragmentWishlistBinding::inflate) {

    private val wishlistViewModel: WishlistViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = getKoin().getProperty("access_token", "")

        if (token == "") {
            binding.shimmerNotification.stopShimmer()
            binding.shimmerNotification.visibility = View.GONE
            binding.rvWhistlist.visibility = View.GONE
            binding.tvFavourite.visibility = View.GONE
            binding.tvLogin.text = "Silakan Login Dahulu"

            binding.btnLogin.setOnClickListener {
                it.findNavController().navigate(R.id.action_wishlistFragment_to_loginFragment)
            }

        } else {
            binding.ivLogin.visibility = View.GONE
            binding.tvLogin.visibility = View.GONE
            binding.btnLogin.visibility = View.GONE
            binding.rvWhistlist.visibility = View.VISIBLE
            binding.shimmerNotification.visibility = View.GONE
            setUpObserver()
            wishlistViewModel.getWishlist()
        }
    }


    private fun setUpObserver() {

        wishlistViewModel.getWishlist.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.shimmerNotification.startShimmer()
                    binding.shimmerNotification.visibility = View.VISIBLE
                    binding.rvWhistlist.visibility = View.GONE
                    binding.ivLogin.visibility = View.GONE
                    binding.tvLogin.visibility = View.GONE
                    binding.btnLogin.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.shimmerNotification.stopShimmer()
                    binding.shimmerNotification.visibility = View.GONE
                    binding.rvWhistlist.visibility = View.VISIBLE
                    if (it.data?.body() != null) {
                        if (it.data.body()?.size == 0) {
                            binding.tvLogin.text = "Tidak ada notifikasi"
                            binding.ivLogin.visibility = View.VISIBLE
                            binding.tvLogin.visibility = View.VISIBLE
                            binding.rvWhistlist.visibility = View.GONE
                        } else {
                            binding.ivLogin.visibility = View.GONE
                            binding.tvLogin.visibility = View.GONE
                            binding.btnLogin.visibility = View.GONE
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

    private fun showBidProduct(data: GetWishlist?) {
        val adapter = WishlistAdapter(
            object : WishlistAdapter.OnClickListener {
                override fun onClickItem(
                    data: GetWishlistItem
                ) {
                    if (data.Product != null) {
                        val id = data.product_id
                        val action =
                            WishlistFragmentDirections.actionWishlistFragmentToDetailProductFragment(id)
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
        binding.rvWhistlist.adapter = adapter
    }


}