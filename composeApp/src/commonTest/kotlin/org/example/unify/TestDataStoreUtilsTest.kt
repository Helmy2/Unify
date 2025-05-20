package org.example.unify

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope

expect fun createTestScope(): CoroutineScope
expect fun createTestDataStore(testName: String, scope: CoroutineScope): DataStore<Preferences>
expect fun clearTestDataStoreFile(testName: String)