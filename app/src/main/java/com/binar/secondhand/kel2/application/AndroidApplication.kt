package com.binar.secondhand.kel2.application

import android.app.Application
import com.binar.secondhand.kel2.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AndroidApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AndroidApplication)
            modules(networkModule, databaseModule, datastoreModule, repositoryModule, viewModelModule)
        }
    }


}