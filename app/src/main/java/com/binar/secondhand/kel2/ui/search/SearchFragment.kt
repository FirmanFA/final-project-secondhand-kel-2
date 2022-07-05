package com.binar.secondhand.kel2.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentSearchBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {


    private val searchViewModel by viewModel<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel.getProduct(searchKeyword = "baju")

        setUpSearchBarListener()
        setUpObserver()
    }

    private fun setUpObserver() {
        searchViewModel.searchResponse.observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        200 -> {
                            val data = it.data.body()
                            showProductList(data)
                        }

                        else ->{
                            showSnackbar("Error occured: ${it.data?.code()}")
                        }
                    }
                }
                Status.ERROR -> {
                    showSnackbar("Error occured: ${it.data?.code()}")
                }
            }
        }
    }

    private fun showProductList(productResponse: GetProductResponse?) {
        val adapter = SearchAdapter {
            //onclick item
            val action = MainFragmentDirections.actionMainFragmentToDetailProductFragment(it.id)
            findNavController().navigate(action)
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