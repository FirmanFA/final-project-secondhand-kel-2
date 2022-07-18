package com.binar.secondhand.kel2.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
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
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
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

        homeViewModel.getHomeBanner()
        homeViewModel.getHomeCategory()

        val productAdapter = HomeProductAdapter {
            val action = MainFragmentDirections.actionMainFragmentToDetailProductFragment(it.id)
            findNavController().navigate(action)
        }
        val productLoadStateAdapter = ProductLoadStateAdapter { productAdapter.retry() }

        binding.bindList(
            header = productLoadStateAdapter,
            productAdapter = productAdapter,
            pagingData = homeViewModel.getProducts()
        )


        binding.headerAppbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            binding.swipeLayout.isEnabled = verticalOffset == 0
        })

        binding.swipeLayout.setOnRefreshListener {
            val categoryId =
                if (binding.tabHomeCategory.getTabAt(binding.tabHomeCategory.selectedTabPosition)
                        ?.id == -1) {

                    null

                } else {
                    binding.tabHomeCategory.getTabAt(binding.tabHomeCategory.selectedTabPosition)
                        ?.id
                }

            binding.bindList(
                header = productLoadStateAdapter,
                productAdapter = productAdapter,
                pagingData = homeViewModel.getProducts(categoryId)
            )
        }

        binding.btnWishlist.setOnClickListener{
            it.findNavController().navigate(R.id.action_mainFragment_to_wishlistFragment)
        }


        binding.etSearch.setOnClickListener {

            val currentDestination =
                this.findNavController().currentDestination?.id == R.id.mainFragment
            if (currentDestination) {
                this.findNavController().navigate(R.id.action_mainFragment_to_searchPageFragment)
            }


        }

        binding.tabHomeCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                //do filter product here
                if (tab?.id == -1) {
//                    homeViewModel.getHomeProduct()
                    binding.bindList(
                        header = productLoadStateAdapter,
                        productAdapter = productAdapter,
                        pagingData = homeViewModel.getProducts()
                    )
                } else {
//                    homeViewModel.getHomeProduct(categoryId = tab?.id)
                    binding.bindList(
                        header = productLoadStateAdapter,
                        productAdapter = productAdapter,
                        pagingData = homeViewModel.getProducts(tab?.id)
                    )
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
                            showSnackbar("Internal server error")
                        }
                    }
                }

                Status.ERROR -> {
                    binding.pbBanner.visibility = View.GONE
                    showSnackbar("Interal server error")
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
                            showSnackbar("Internal server error")
                        }
                    }
                }

                Status.ERROR -> {
//                    binding.pbCategory.visibility = View.GONE
                    binding.shimmerCategory.visibility = View.GONE
                    binding.shimmerCategory.stopShimmer()
                    showSnackbar("Internal server error")
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

    private fun FragmentHomeBinding.bindList(
        header: ProductLoadStateAdapter,
        productAdapter: HomeProductAdapter,
        pagingData: Flow<PagingData<UiModel.ProductItem>>
    ) {

        val footerAdapter = ProductLoadStateAdapter { productAdapter.retry() }
        rvHomeProduct.adapter = productAdapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = footerAdapter
        )

        lifecycleScope.launchWhenCreated {
            productAdapter.loadStateFlow.collectLatest {
                binding.swipeLayout.isRefreshing = it.refresh is LoadState.Loading
            }
        }

        val gridLayoutManager = rvHomeProduct.layoutManager as GridLayoutManager
        gridLayoutManager.spanSizeLookup =  object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if ((position == productAdapter.itemCount  && footerAdapter.itemCount > 0) ||
                    (position == productAdapter.itemCount  && header.itemCount > 0)) {
                    2
                } else {
                    1
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            pagingData.collectLatest(productAdapter::submitData)
        }

        lifecycleScope.launch {
            productAdapter.loadStateFlow.collect { loadState ->
                // Show a retry header if there was an error refreshing, and items were previously
                // cached OR default to the default prepend state
                header.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && productAdapter.itemCount > 0 }
                    ?: loadState.prepend

                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && productAdapter.itemCount == 0
                // show empty list
//                emptyList.isVisible = isListEmpty
                // Only show the list if refresh succeeds, either from the the local db or the remote.
                rvHomeProduct.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                            || loadState.mediator?.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                pbHomeProduct.isVisible = loadState.mediator?.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
//                retryButton.isVisible = loadState.mediator?.refresh is LoadState.Error && productAdapter.itemCount == 0
                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        context,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


}