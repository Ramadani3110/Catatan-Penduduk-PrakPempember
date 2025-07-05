package com.rams.catatanpenduduk.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.first

class TokenManager private constructor(private val dataStore: DataStore<Preferences>) {

    companion object{
        private const val PREFS_NAME = "bearer_token_prefs"
        private val TOKEN_KEY = stringPreferencesKey("bearer_token")

        @Volatile
        private var INSTANCE: TokenManager? = null
        fun getInstance(context: Context): TokenManager{
            return INSTANCE ?: synchronized(this){
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile(PREFS_NAME)
                }
                TokenManager(dataStore).also { INSTANCE = it }
            }
        }
    }

    private var cachedToken: String? = null

    suspend fun saveToken(token: String){
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
        cachedToken = token
    }

    suspend fun getToken(): String? {
        cachedToken?.let { return it }
        val preferences = dataStore.data.first()
        val token = preferences[TOKEN_KEY]
        cachedToken = token
        return token
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
        cachedToken = null
    }

}