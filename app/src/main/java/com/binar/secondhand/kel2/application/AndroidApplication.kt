package com.binar.secondhand.kel2.application

import android.app.Application
import android.content.Context
import com.binar.secondhand.kel2.di.*
import com.binar.secondhand.kel2.ui.login.LoginFragment
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AndroidApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val preferences =
            this.getSharedPreferences(LoginFragment.LOGINUSER, Context.MODE_PRIVATE)
        val token = preferences.getString(LoginFragment.TOKEN, "")
        startKoin {
            androidLogger()
            androidContext(this@AndroidApplication)
            modules(networkModule, databaseModule, datastoreModule, repositoryModule, viewModelModule)
            if (!token.isNullOrEmpty()){
                koin.setProperty("access_token", token)
            }
        }


    }


}