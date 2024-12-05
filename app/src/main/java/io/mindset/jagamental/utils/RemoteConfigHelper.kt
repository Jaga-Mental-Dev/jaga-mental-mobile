package io.mindset.jagamental.utils

import android.util.Log
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import io.mindset.jagamental.R
import kotlinx.coroutines.tasks.await

class RemoteConfigHelper {

    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance().apply {
        setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()
        )
        setDefaultsAsync(R.xml.remote_config_defaults)
    }

    suspend fun fetchAndActivate(): Boolean {
        return try {
            remoteConfig.fetchAndActivate().await()
        } catch (e: Exception) {
            false
        }
    }

    suspend fun listenForUpdates() {
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.i("RemoteConfig", "onUpdate: ${configUpdate.updatedKeys}")
                remoteConfig.activate().addOnCompleteListener {
                    Log.i("RemoteConfig", "onUpdate: Activated")
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.i("RemoteConfig", "onError: ${error.message}")
            }
        })
    }

    fun getString(key: String): String = remoteConfig.getString(key)
    fun getBoolean(key: String): Boolean = remoteConfig.getBoolean(key)
    fun getLong(key: String): Long = remoteConfig.getLong(key)
    fun getDouble(key: String): Double = remoteConfig.getDouble(key)
}
