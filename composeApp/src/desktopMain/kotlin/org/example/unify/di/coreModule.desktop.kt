package org.example.unify.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.example.unify.core.util.Connectivity
import org.example.unify.core.util.ConnectivityImp
import org.example.unify.core.util.PREFERENCES_NAME
import org.example.unify.core.util.createDataStore
import org.example.unify.core.util.getPlatformPath
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Connectivity> {
        ConnectivityImp()
    }
    single<DataStore<Preferences>> {
        createDataStore(
            producePath = { getPlatformPath() + PREFERENCES_NAME }
        )
    }
}

