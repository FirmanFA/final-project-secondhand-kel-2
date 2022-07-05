package com.binar.secondhand.kel2.di

import com.binar.secondhand.kel2.data.repository.*
import com.binar.secondhand.kel2.ui.pass.ChangePassViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {

    singleOf(::Repository)

    singleOf(::LoginRepository)

    singleOf(::RegisterRepository)

    singleOf(::HomeRepository)

    singleOf(::ProductSaleListRepository)
}