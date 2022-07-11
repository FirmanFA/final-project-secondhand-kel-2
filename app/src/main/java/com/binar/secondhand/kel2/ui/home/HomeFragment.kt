package com.binar.secondhand.kel2.ui.home

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponse
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentHomeBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
import com.binar.secondhand.kel2.ui.main.MainFragmentDirections
import com.binar.secondhand.kel2.utils.HorizontalMarginItemDecoration
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import kotlin.math.abs

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_home

        setUpSearchBarListener()
        setUpObserver()

        val token = getKoin().getProperty("access_token","")

        if (token.isNotEmpty()){
            homeViewModel.getAuth()
            binding.tvUserName.setOnClickListener {
                it.findNavController().navigate(R.id.action_mainFragment_to_profileFragment2)
            }
            binding.ivProfilePhoto.setOnClickListener {
                it.findNavController().navigate(R.id.action_mainFragment_to_profileFragment2)
            }
        }else{
            binding.tvUserName.text = "Click to login"
            binding.tvUserName.setOnClickListener {
                it.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            }
        }

        homeViewModel.getHomeBanner()
        homeViewModel.getHomeCategory()

        binding.etSearch.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_searchPageFragment)
        }

        binding.tabHomeCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                //do filter product here
                if (tab?.id == -1){
                    homeViewModel.getHomeProduct()
                }else{
                    homeViewModel.getHomeProduct(categoryId = tab?.id)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    private fun setupBannerViewPager(){


    }

    private fun setUpObserver() {
        homeViewModel.getBannerResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                    binding.pbBanner.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {

                    binding.pbBanner.visibility = View.GONE

                    when (it.data?.code()) {
                        200 -> {
                            val data = it.data.body()
                            showHomeBanner(data)
                        }

                        else ->{
                            showSnackbar("Error occured: ${it.data?.code()}")
                        }
                    }
                }

                Status.ERROR -> {
                    binding.pbBanner.visibility = View.GONE
                    showSnackbar("${it.message}")
                }
            }
        }

        homeViewModel.getCategoryResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
//                    binding.pbCategory.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {

//                    binding.pbCategory.visibility = View.GONE

                    when (it.data?.code()) {
                        200 -> {

                            binding.tabHomeCategory.addTab(
                                binding.tabHomeCategory.newTab()
                                    .setText("Semua")
                                    .setIcon(R.drawable.ic_baseline_search_24)
                                    .setId(-1)
                            )

                            val data = it.data.body()
                            if (data != null) {
                                for (category in data) {
                                    binding.tabHomeCategory.addTab(
                                        binding.tabHomeCategory.newTab()
                                            .setText(category.name)
                                            .setIcon(R.drawable.ic_baseline_search_24)
                                            .setId(category.id)
                                    )

                                }
                            }
                        }

                        else ->{
                            showSnackbar("Error occured: ${it.data?.code()}")
                        }
                    }
                }

                Status.ERROR -> {
//                    binding.pbCategory.visibility = View.GONE
                    showSnackbar("${it.message}")
                }
            }
        }

        homeViewModel.getHomeProductResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                    binding.pbHomeProduct.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {

                    binding.pbHomeProduct.visibility = View.GONE

                    when (it.data?.code()) {
                        200 -> {

                            val data = it.data.body()
                            showHomeProductList(data)

                        }
                        else ->{
                            showSnackbar("Error occured: ${it.data?.code()}")
                        }
                    }
                }

                Status.ERROR -> {
                    binding.pbHomeProduct.visibility = View.GONE
                    showSnackbar("${it.message}")
                }
            }
        }
        homeViewModel.authGetResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                }

                Status.SUCCESS -> {

                    when (it.data?.code()) {
                        200 -> {
                            Glide.with(requireContext())
                                .load(it.data.body()?.imageUrl)
                                .circleCrop()
                                .placeholder(R.drawable.round_camera)
                                .error(R.drawable.round_camera)
                                .into(binding.ivProfilePhoto)

                            binding.tvUserName.text = it.data.body()?.fullName
                        }
                    }
                }

                Status.ERROR -> {
                    val error = it.message
                    Toast.makeText(
                        requireContext(),
                        "Error get Data : $error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showHomeBanner(data: GetBannerResponse?) {
        if (data?.size == 0){
            binding.apply {
                ivEmpty.visibility = View.VISIBLE
                tvEmpty.visibility = View.VISIBLE
                rvHomeProduct.visibility = View.INVISIBLE
            }
        }else{
            binding.apply {
                ivEmpty.visibility = View.GONE
                tvEmpty.visibility = View.GONE
                rvHomeProduct.visibility = View.VISIBLE
            }
            val adapter = HomeBannerAdapter {
                //onclick item
            }

            binding.vpHomeBanner.adapter = adapter
            binding.vpHomeBanner.offscreenPageLimit = 1
            val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
            val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
            val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
            val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->

                page.translationX = -pageTranslationX * position
                page.scaleY = 1 - (0.25f * abs(position))

            }
            binding.vpHomeBanner.setPageTransformer(pageTransformer)

            val itemDecoration = HorizontalMarginItemDecoration(
                requireContext(),
                R.dimen.viewpager_current_item_horizontal_margin
            )

            binding.vpHomeBanner.addItemDecoration(itemDecoration)

            adapter.submitList(data)
        }
    }

    private fun showHomeProductList(productResponse: GetProductResponse?) {
        val adapter = HomeProductAdapter {
            //onclick item
            val action = MainFragmentDirections.actionMainFragmentToDetailProductFragment(it.id)
            findNavController().navigate(action)
        }

        adapter.submitList(productResponse)

        binding.rvHomeProduct.adapter = adapter

    }


    private fun setUpSearchBarListener() {
//        binding.etSearch.setEndIconOnClickListener {
//            //do search
//            searchProduct(binding.etSearch.editText?.text.toString())
//        }
//
//        binding.etSearch.editText?.setOnEditorActionListener { _, i, _ ->
//
//            if (i == EditorInfo.IME_ACTION_SEARCH) {
//                //do something with search
//                searchProduct(binding.etSearch.editText?.text.toString())
//            }
//
//            true
//        }
    }

    private fun searchProduct(query: String) {
        //TODO
    }


}