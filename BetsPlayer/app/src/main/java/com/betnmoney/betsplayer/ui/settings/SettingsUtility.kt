package com.betnmoney.betsplayer.ui.settings

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class SettingsUtility(private val onFetch: (game: String) -> Unit) {

    private val remoteConfig = Firebase.remoteConfig
    private val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 3600 }
    var game = ""

    fun fetch() {
        remoteConfig.apply {
            fetch(0)
            setConfigSettingsAsync(configSettings)
            remoteConfig.fetchAndActivate()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) game = getString("url")
                    onFetch.invoke(game)
                }
        }
    }
}