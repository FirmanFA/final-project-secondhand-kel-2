package com.binar.secondhand.kel2.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import kotlin.math.abs

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_home

        setUpObserver()

        val token = getKoin().getProperty("access_token", "")

        if (token.isNotEmpty()) {
            homeViewModel.getAuth()
            binding.tvUserName.setOnClickListener {
                it.findNavController().navigate(R.id.action_mainFragment_to_profileFragment2)
            }
            binding.ivProfilePhoto.setOnClickListener {
                it.findNavController().navigate(R.id.action_mainFragment_to_profileFragment2)
            }
        } else {
            binding.tvUserName.text = "Click to login"
            binding.tvUserName.setOnClickListener {
                it.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            }
        }

//        binding.

        homeViewModel.getHomeBanner()
        homeViewModel.getHomeCategory()

        binding.etSearch.setOnClickListener {

            val currentDestination =
                this.findNavController().currentDestination?.id == R.id.mainFragment
            if (currentDestination){
                this.findNavController().navigate(R.id.action_mainFragment_to_searchPageFragment)
            }


        }

        binding.tabHomeCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                //do filter product here
                if (tab?.id == -1) {
                    homeViewModel.getHomeProduct()
                } else {
                    homeViewModel.getHomeProduct(categoryId = tab?.id)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    private fun setupBannerViewPager() {


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

                        else -> {
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
                    binding.shimmerCategory.visibility = View.VISIBLE
                    binding.shimmerCategory.startShimmer()
                }

                Status.SUCCESS -> {

//                    binding.pbCategory.visibility = View.GONE
                    binding.shimmerCategory.visibility = View.GONE
                    binding.shimmerCategory.stopShimmer()

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

                        else -> {
                            showSnackbar("Error occured: ${it.data?.code()}")
                        }
                    }
                }

                Status.ERROR -> {
//                    binding.pbCategory.visibility = View.GONE
                    binding.shimmerCategory.visibility = View.GONE
                    binding.shimmerCategory.stopShimmer()
                    showSnackbar("${it.message}")
                }
            }
        }

        homeViewModel.getHomeProductResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                    binding.rvHomeProduct.visibility = View.GONE
                    binding.pbHomeProduct.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {

                    binding.pbHomeProduct.visibility = View.GONE
                    binding.rvHomeProduct.visibility = View.VISIBLE

                    when (it.data?.code()) {
                        200 -> {

                            val data = it.data.body()
                            showHomeProductList(data)

                        }
                        else -> {
                            showSnackbar("Error occured: ${it.data?.code()}")
                        }
                    }
                }

                Status.ERROR -> {
                    binding.pbHomeProduct.visibility = View.GONE
                    binding.rvHomeProduct.visibility = View.VISIBLE
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
        if (data?.size == 0) {
            binding.apply {
                ivEmpty.visibility = View.VISIBLE
                tvEmpty.visibility = View.VISIBLE
                rvHomeProduct.visibility = View.INVISIBLE
            }
        } else {
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
            val currentItemHorizontalMarginPx =
                resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
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

//        adapter.submitList(productResponse)

        binding.rvHomeProduct.adapter = adapter

    }

    private fun FragmentHomeBinding.bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<UiModel>>,
        uiActions: (UiAction) -> Unit
    ) {
        val repoAdapter = HomeProductAdapter {}
        val header = ProductLoadStateAdapter { repoAdapter.retry() }
        list.adapter = repoAdapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = ReposLoadStateAdapter { repoAdapter.retry() }
        )
        bindSearch(
            uiState = uiState,
            onQueryChanged = uiActions
        )
        bindList(
            header = header,
            repoAdapter = repoAdapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions
        )
    }

    private fun ActivitySearchRepositoriesBinding.bindSearch(
        uiState: StateFlow<UiState>,
        onQueryChanged: (UiAction.Search) -> Unit
    ) {
        searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }
        searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            uiState
                .map { it.query }
                .distinctUntilChanged()
                .collect(searchRepo::setText)
        }
    }

    private fun ActivitySearchRepositoriesBinding.updateRepoListFromInput(onQueryChanged: (UiAction.Search) -> Unit) {
        searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                list.scrollToPosition(0)
                onQueryChanged(UiAction.Search(query = it.toString()))
            }
        }
    }

    private fun ActivitySearchRepositoriesBinding.bindList(
        header: ReposLoadStateAdapter,
        repoAdapter: ReposAdapter,
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<UiModel>>,
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {
        retryButton.setOnClickListener { repoAdapter.retry() }
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.query))
            }
        })
        val notLoading = repoAdapter.loadStateFlow
            .asRemotePresentationState()
            .map { it == RemotePresentationState.PRESENTED }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        )
            .distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collectLatest(repoAdapter::submitData)
        }

        lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) list.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            repoAdapter.loadStateFlow.collect { loadState ->
                // Show a retry header if there was an error refreshing, and items were previously
                // cached OR default to the default prepend state
                header.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && repoAdapter.itemCount > 0 }
                    ?: loadState.prepend

                val isListEmpty = loadState.refresh is LoadState.NotLoading && repoAdapter.itemCount == 0
                // show empty list
                emptyList.isVisible = isListEmpty
                // Only show the list if refresh succeeds, either from the the local db or the remote.
                list.isVisible =  loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                retryButton.isVisible = loadState.mediator?.refresh is LoadState.Error && repoAdapter.itemCount == 0
                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        this@SearchRepositoriesActivity,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


}