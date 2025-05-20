package org.example.unify

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.File

actual fun createTestScope(): CoroutineScope = CoroutineScope(Dispatchers.Unconfined + SupervisorJob()) // Or your preferred test dispatcher

actual fun createTestDataStore(testName: String, scope: CoroutineScope): DataStore<Preferences> {
    val tempDir = System.getProperty("java.io.tmpdir")
    val file = File(tempDir, "${testName}.preferences_pb")
    // Ensure the file is deleted if it exists from a previous failed run
    file.delete()
    return PreferenceDataStoreFactory.create(scope = scope) { file }
}

actual fun clearTestDataStoreFile(testName: String) {
    val tempDir = System.getProperty("java.io.tmpdir")
    val file = File(tempDir, "${testName}.preferences_pb")
    file.delete()
}