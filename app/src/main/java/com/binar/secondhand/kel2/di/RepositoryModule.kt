package com.binar.secondhand.kel2.di

import com.binar.secondhand.kel2.data.repository.ExampleRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {

    singleOf(::ExampleRepository)
}