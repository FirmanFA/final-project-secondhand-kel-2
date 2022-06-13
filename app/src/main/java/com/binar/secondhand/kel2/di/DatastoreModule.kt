package com.binar.secondhand.kel2.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DATA_STORE_NAME = "DATA_STORE_NAME"

val datastoreModule = module {

    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile(DATA_STORE_NAME)
        }
    }

    //create datastoremanager instance here

}