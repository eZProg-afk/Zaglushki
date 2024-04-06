package com.betnmoney.betsplayer.di

import android.content.Context
import androidx.room.Room
import com.betnmoney.betsplayer.data.AppDatabase
import org.koin.dsl.module

fun databaseModule(context: Context) = module {
    single(createdAtStart = true) {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "bets_player_db"
        ).fallbackToDestructiveMigration().build()
    }

    single {
        get<AppDatabase>().historyDao()
    }
}