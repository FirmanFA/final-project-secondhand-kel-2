package com.binar.secondhand.kel2.di

import com.binar.secondhand.kel2.data.repository.LoginRepository
import com.binar.secondhand.kel2.data.repository.RegisterRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {

    singleOf(::LoginRepository)

    singleOf(::RegisterRepository)

}