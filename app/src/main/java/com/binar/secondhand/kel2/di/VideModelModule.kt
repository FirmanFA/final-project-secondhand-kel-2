package com.binar.secondhand.kel2.di


import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.binar.secondhand.kel2.ui.detail.DetailProductViewModel
import com.binar.secondhand.kel2.ui.detail.BuyerPenawaranViewModel
import com.binar.secondhand.kel2.ui.home.HomeViewModel
import com.binar.secondhand.kel2.ui.login.LoginViewModel
import com.binar.secondhand.kel2.ui.notification.NotificationViewModel
import com.binar.secondhand.kel2.ui.profile.ProfileViewModel
import com.binar.secondhand.kel2.ui.register.RegisterViewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::LoginViewModel)

    viewModelOf(::RegisterViewModel)

    viewModelOf(::ProfileViewModel)

    viewModelOf(::HomeViewModel)

    viewModelOf(::NotificationViewModel)


    viewModelOf(::DetailProductViewModel)
    viewModelOf(::BuyerPenawaranViewModel)
}