package com.binar.secondhand.kel2.ui.home

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponse
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentHomeBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        getKoin().setProperty(
//            "access_token",
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVtYWlsa2VsMkBtYWlsLmNvbSIsImlh" +
//                    "dCI6MTY1NTgwODc4NX0.uCRi6CtzaaaoMNLLD8C8eYMrwnZx3aZAFgi2_Ey6o1w"
//        )

        setUpSearchBarListener()
        setUpObserver()

//        homeViewModel.getHomeProduct()
        homeViewModel.getHomeBanner()
        homeViewModel.getHomeCategory()



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
                    binding.pbCategory.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {

                    binding.pbCategory.visibility = View.GONE

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
                    binding.pbCategory.visibility = View.GONE
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
    }

    private fun showHomeBanner(data: GetBannerResponse?) {
        val adapter = HomeBannerAdapter {
            //onclick item
        }

        adapter.submitList(data)

        binding.vpHomeBanner.adapter = adapter
    }

    private fun showHomeProductList(productResponse: GetProductResponse?) {
        val adapter = HomeProductAdapter {
            //onclick item
        }

        adapter.submitList(productResponse)

        binding.rvHomeProduct.adapter = adapter

    }


    private fun setUpSearchBarListener() {
        binding.etSearch.setEndIconOnClickListener {
            //do search
            searchProduct(binding.etSearch.editText?.text.toString())
        }

        binding.etSearch.editText?.setOnEditorActionListener { _, i, _ ->

            if (i == EditorInfo.IME_ACTION_SEARCH) {
                //do something with search
                searchProduct(binding.etSearch.editText?.text.toString())
            }

            true
        }
    }

    private fun searchProduct(query: String) {
        //TODO
    }


}