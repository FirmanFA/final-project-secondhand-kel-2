package com.binar.secondhand.kel2.ui.home

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetProductResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentHomeBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSearchBarListener()
        setUpObserver()

        homeViewModel.getHomeProduct()
        homeViewModel.getHomeBanner()
        homeViewModel.getHomeCategory()

        for (i in 0..3) {
            binding.tabHomeCategory.addTab(
                binding.tabHomeCategory.newTab()
                    .setText("tab ke $i")
                    .setIcon(R.drawable.ic_baseline_search_24).setId(i*10)
                    .setTag("ini tag tab ke $i")
            )

        }

        binding.tabHomeCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Toast.makeText(context, "${tab?.tag}", Toast.LENGTH_SHORT).show()
                //do filter product here
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    private fun setUpObserver() {
        homeViewModel.getBannerResponse.observe(viewLifecycleOwner){
            when(it.status){

                Status.LOADING -> {}

                Status.SUCCESS -> {

                    when(it.data?.code()){
                        200 ->{
                            val data = it.data.body()
                            showHomeBanner(data)
                        }
                    }
                }

                Status.ERROR ->{}
            }
        }

        homeViewModel.getCategoryResponse.observe(viewLifecycleOwner){
            when(it.status){

                Status.LOADING -> {}

                Status.SUCCESS -> {

                    when(it.data?.code()){
                        200 ->{

                            val data = it.data.body()
                            if (data != null) {
                                for (category in data) {
                                    binding.tabHomeCategory.addTab(
                                        binding.tabHomeCategory.newTab()
                                            .setText(category.name)
                                            .setIcon(R.drawable.ic_baseline_search_24)
                                            .setId(category.id)
                                            .setTag(category.name)
                                    )

                                }
                            }

                        }
                    }
                }

                Status.ERROR ->{}
            }
        }

        homeViewModel.getHomeProductResponse.observe(viewLifecycleOwner){
            when(it.status){

                Status.LOADING -> {}

                Status.SUCCESS -> {

                    when(it.data?.code()){
                        200 ->{

                            val data = it.data.body()
                            showHomeProductList(data)

                        }
                    }
                }

                Status.ERROR ->{}
            }
        }
    }

    private fun showHomeBanner(data: GetBannerResponse?) {
        val adapter = HomeBannerAdapter{
            //onclick item
        }

        adapter.submitList(data)

        binding.vpHomeBanner.adapter = adapter
    }

    private fun showHomeProductList(productResponse: GetProductResponse?) {
        val adapter = HomeProductAdapter{
            //onclick item
        }

        adapter.submitList(productResponse)

        binding.rvHomeProduct.adapter = adapter

    }



    private fun setUpSearchBarListener(){
        binding.etSearch.setEndIconOnClickListener {
            //do search
            searchProduct(binding.etSearch.editText?.text.toString())
        }

        binding.etSearch.editText?.setOnEditorActionListener { textView, i, keyEvent ->

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