package com.binar.secondhand.kel2.di

import com.binar.secondhand.kel2.ui.example.ExampleViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::ExampleViewModel)

}