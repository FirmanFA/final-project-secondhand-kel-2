package com.binar.secondhand.kel2.di


import com.binar.secondhand.kel2.ui.account.AccountViewModel
import com.binar.secondhand.kel2.ui.bidder.BidderViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.binar.secondhand.kel2.ui.wishlist.WishlistViewModel
import com.binar.secondhand.kel2.ui.detail.DetailProductViewModel
import com.binar.secondhand.kel2.ui.edit.EditViewModel
import com.binar.secondhand.kel2.ui.home.HomeViewModel
import com.binar.secondhand.kel2.ui.lengkapi.SellerDetailProductViewModel
import com.binar.secondhand.kel2.ui.login.LoginViewModel
import com.binar.secondhand.kel2.ui.main.MainViewModel
import com.binar.secondhand.kel2.ui.notification.NotificationViewModel
import com.binar.secondhand.kel2.ui.order.MyOrderViewModel
import com.binar.secondhand.kel2.ui.pass.ChangePassViewModel
import com.binar.secondhand.kel2.ui.preview.PreviewViewModel
import com.binar.secondhand.kel2.ui.sale.main.ProductSaleListViewModel
import com.binar.secondhand.kel2.ui.profile.ProfileViewModel
import com.binar.secondhand.kel2.ui.register.RegisterViewModel
import com.binar.secondhand.kel2.ui.search.page.SearchPageViewModel
import com.binar.secondhand.kel2.ui.search.result.SearchViewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::LoginViewModel)

    viewModelOf(::RegisterViewModel)

    viewModelOf(::ProfileViewModel)

    viewModelOf(::HomeViewModel)

    viewModelOf(::NotificationViewModel)

    viewModelOf(::DetailProductViewModel)

    viewModelOf(::SellerDetailProductViewModel)

    viewModelOf(::PreviewViewModel)

    viewModelOf(::ProductSaleListViewModel)

    viewModelOf(::ChangePassViewModel)

    viewModelOf(::SearchPageViewModel)

    viewModelOf(::SearchViewModel)

    viewModelOf(::AccountViewModel)

    viewModelOf(::BidderViewModel)

    viewModelOf(::EditViewModel)

    viewModelOf(::MainViewModel)

    viewModelOf(::WishlistViewModel)

    viewModelOf(::MyOrderViewModel)

}