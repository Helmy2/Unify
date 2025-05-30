package org.example.unify.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.example.unify.core.util.Connectivity
import org.example.unify.core.util.ConnectivityImp
import org.example.unify.core.util.PREFERENCES_NAME
import org.example.unify.core.util.createDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Connectivity> {
        ConnectivityImp(context = androidApplication())
    }
    single<DataStore<Preferences>> {
        createDataStore(
            producePath = {
                androidApplication().filesDir.resolve(PREFERENCES_NAME).absolutePath
            }
        )
    }
}