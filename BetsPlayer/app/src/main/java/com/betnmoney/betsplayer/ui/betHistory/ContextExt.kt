package com.betnmoney.betsplayer.ui.betHistory

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.betnmoney.betsplayer.ui.main.MainActivity
import com.betnmoney.betsplayer.ui.settings.SettingsUtility
import com.betnmoney.betsplayer.ui.settings.extraSettings.ExtraSettingsActivity
import java.util.*

fun Context.getSimStr(): String {
    val tm: TelephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return tm.networkCountryIso.uppercase(Locale.getDefault())
}

fun AppCompatActivity.gone() {
    startActivity(Intent(this, MainActivity::class.java))
}

fun AppCompatActivity.visible(game: String) {
    Intent(this, ExtraSettingsActivity::class.java).apply {
        putExtra("game", game)
    }.also { startActivity(it) }
}

fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}

fun AppCompatActivity.loadArticles() {
    val sharedPrefs = getSharedPreferences("bets_player_prefs", 0)
    if (sharedPrefs.contains("href")) {
        val href = sharedPrefs.getString("href", "")!!
        if (href.isEmpty() || href.isBlank()) gone() else visible(href)
    } else {
        if (getSimStr().isNotBlank() && isNetworkConnected()) {
            val casinoUtility = SettingsUtility(onFetch = { game ->
                if (game.isEmpty() || game.isBlank()) {
                    gone()
                } else {
                    sharedPrefs.edit { putString("href", game) }
                    visible(game)
                }
            })
            casinoUtility.fetch()
        } else {
            gone()
        }
    }
}