package org.example.unify.di

import kotlinx.cinterop.ExperimentalForeignApi
import org.example.unify.core.util.Connectivity
import org.example.unify.core.util.ConnectivityImp
import org.example.unify.core.util.PREFERENCES_NAME
import org.example.unify.core.util.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL


@OptIn(ExperimentalForeignApi::class)
actual val platformModule: Module = module {
    single<Connectivity> {
        ConnectivityImp()
    }

    single {
        createDataStore(
            producePath = {
                val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                requireNotNull(documentDirectory).path + "/$PREFERENCES_NAME"
            }
        )
    }
}

