package com.binar.secondhand.kel2.ui.search.page

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.data.local.room.model.SearchHistoryEntity
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentSearchPageBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchPageFragment :
    BaseFragment<FragmentSearchPageBinding>(FragmentSearchPageBinding::inflate) {

    private val searchPageViewModel by viewModel<SearchPageViewModel>()
    private lateinit var adapter: SearchHistoryAdapter
    private var listHistory: List<SearchHistoryEntity>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSearchBarListener()

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.etSearch.editText?.requestFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etSearch.editText, InputMethodManager.SHOW_IMPLICIT)

        setUpObserver()
        searchPageViewModel.getSearchHistory()

        setupInputListener()

    }

    private fun setupInputListener() {
        binding.etSearch.editText?.addTextChangedListener { keyword ->
            val filteredList =
                listHistory?.filter { it.searchKeyword.contains(keyword.toString()) }

            adapter.submitList(filteredList)
        }
    }

    private fun setUpObserver() {

        searchPageViewModel.searchResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    listHistory = it.data
                    showSearchHistory(it.data)
                }

                Status.ERROR -> {
                    showSnackbar("${it.message}")
                }
            }
        }

    }

    private fun showSearchHistory(data: List<SearchHistoryEntity>?) {
        adapter = SearchHistoryAdapter {
            //onclick item
            val action =
                SearchPageFragmentDirections
                    .actionSearchPageFragmentToSearchFragment(it.searchKeyword)
            findNavController().navigate(action)
        }

        adapter.submitList(data)

        binding.rvSearchHistory.adapter = adapter
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
        //insert data to room
        if (query.isNotEmpty()){
            searchPageViewModel.insertSearchHistory(SearchHistoryEntity(searchKeyword = query))

            val action = SearchPageFragmentDirections
                .actionSearchPageFragmentToSearchFragment(query)
            findNavController().navigate(action)
        }
    }


}