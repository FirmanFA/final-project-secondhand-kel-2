package com.binar.secondhand.kel2.ui.sale.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetSellerProductResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentProductSaleListBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
import com.binar.secondhand.kel2.ui.main.MainFragmentDirections
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductSaleListFragment :
    BaseFragment<FragmentProductSaleListBinding>(FragmentProductSaleListBinding::inflate) {

    private val productSaleListViewModel: ProductSaleListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_sale_list
        if (MainFragment.statusTerbit == "sukses") {
            val snackbar =
                Snackbar.make(binding.snackbar, "Produk Berhasil Terbit", Snackbar.LENGTH_LONG)
            snackbar.setAction("x") {
                // Responds to click on the action
                snackbar.dismiss()
            }
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.Green))
            .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            .show()
            MainFragment.statusTerbit = ""
        }

        val token = getKoin().getProperty("access_token", "")
        if (token == "") {
            binding.groupContent.visibility = View.GONE
            binding.groupLogin.visibility = View.VISIBLE
        } else {
            binding.groupLogin.visibility = View.GONE
            binding.btnEditProfile.setOnClickListener {
                it.findNavController().navigate(R.id.action_mainFragment_to_profileFragment2)
            }
            setUpObserver()
            productSaleListViewModel.getAuth()
        }

        binding.btnLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

        binding.vpList.adapter = ProductViewPagerAdapter(this)
        TabLayoutMediator(binding.tabProductFilter, binding.vpList) { tab, pos ->
            when (pos) {
                0 -> {
                    tab.text = "Product"
                    tab.setIcon(R.drawable.ic_product)
                }
                1 -> {
                    tab.text = "Diminati"
                    tab.setIcon(R.drawable.ic_fav)
                }
                2 -> {
                    tab.text = "Terjual"
                    tab.setIcon(R.drawable.ic_dollar)
                }
            }
        }.attach()
    }

    private fun setUpObserver() {
        productSaleListViewModel.authGetResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                }

                Status.SUCCESS -> {

                    when (it.data?.code()) {
                        200 -> {

                            val shimmer =
                                Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                                    .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                                    .setBaseAlpha(0.7f) //the alpha of the underlying children
                                    .setHighlightAlpha(0.6f) // the shimmer alpha amount
                                    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                                    .setAutoStart(true)
                                    .build()
                            val shimmerDrawable = ShimmerDrawable().apply {
                                setShimmer(shimmer)
                            }

                            Glide.with(requireContext())
                                .load(it.data.body()?.imageUrl)
                                .placeholder(shimmerDrawable)
                                .transform(CenterCrop(), RoundedCorners(12))
                                .error(R.drawable.rectangle_camera)
                                .into(binding.ivUserPhoto)

                            binding.tvUserName.text = it.data.body()?.fullName
                            binding.tvUserCity.text = it.data.body()?.city
                        }
                    }
                }

                Status.ERROR -> {
//                    val error = it.message
//                    Toast.makeText(
//                        requireContext(),
//                        "Error get Data : $error",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }
        }
    }

}