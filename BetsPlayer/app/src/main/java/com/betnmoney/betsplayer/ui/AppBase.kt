package com.betnmoney.betsplayer.ui

import android.app.Application
import com.betnmoney.betsplayer.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppBase : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AppBase)
            modules(databaseModule(this@AppBase))
        }
    }
}