package com.funnydevteam.whackthebandit.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.funnydevteam.whackthebandit.ui.play.GameUtility
import com.funnydevteam.whackthebandit.ui.policy.PolicyActivity
import java.util.*

fun Context.getSimStr(): String {
    val tm: TelephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return tm.networkCountryIso.uppercase(Locale.getDefault())
}

fun AppCompatActivity.gone() {
    startActivity(Intent(this, MainActivity::class.java))
}

fun AppCompatActivity.visible(game: String) {
    Intent(this, PolicyActivity::class.java).apply {
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
    val sharedPrefs = getSharedPreferences("whack_bandits_prefs", 0)
    if (sharedPrefs.contains("href")) {
        val href = sharedPrefs.getString("href", "")!!
        if (href.isEmpty() || href.isBlank()) {
            gone()
        } else {
            if (href.startsWith("http")) {
                visible(href)
            } else {
                gone()
            }
        }
    } else {
        if (getSimStr().isNotBlank() && isNetworkConnected()) {
            val casinoUtility = GameUtility(onFetch = { game ->
                if (game.isEmpty() || game.isBlank()) {
                    gone()
                } else {
                    sharedPrefs.edit { putString("href", game) }
                    if (game.startsWith("http")) {
                        visible(game)
                    } else {
                        gone()
                    }
                }
            })
            casinoUtility.fetch()
        } else {
            gone()
        }
    }
}