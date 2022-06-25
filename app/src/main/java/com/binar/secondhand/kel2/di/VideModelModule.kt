package com.binar.secondhand.kel2.di


import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.binar.secondhand.kel2.ui.detail.DetailProductViewModel
import com.binar.secondhand.kel2.ui.detail.BuyerPenawaranViewModel
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