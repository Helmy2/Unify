package org.example.unify

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual fun createTestScope(): CoroutineScope = CoroutineScope(Dispatchers.Unconfined + SupervisorJob()) // Or use MainScope for iOS tests if needed

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private fun getTestFilePath(testName: String): okio.Path {
    val fileManager = NSFileManager.defaultManager
    val cachesDirectory = NSSearchPathForDirectoriesInDomains(
        NSCachesDirectory,
        NSUserDomainMask,
        true
    ).first() as String
    // Ensure the directory exists (it should by default for Caches)
    val testDir = "$cachesDirectory/kmp_datastore_tests"
    fileManager.createDirectoryAtPath(testDir, withIntermediateDirectories = true, attributes = null, error = null)
    return "$testDir/${testName}.preferences_pb".toPath()
}

@OptIn(ExperimentalForeignApi::class)
actual fun createTestDataStore(testName: String, scope: CoroutineScope): DataStore<Preferences> {
    val filePath = getTestFilePath(testName)
    // Ensure clean state by deleting if exists
    NSFileManager.defaultManager.removeItemAtPath(filePath.toString(), null)
    return PreferenceDataStoreFactory.createWithPath (scope = scope ){filePath  }
}

@OptIn(ExperimentalForeignApi::class)
actual fun clearTestDataStoreFile(testName: String) {
    val filePath = getTestFilePath(testName)
    NSFileManager.defaultManager.removeItemAtPath(filePath.toString(), null)
}