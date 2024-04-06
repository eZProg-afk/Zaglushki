package com.betnmoney.betsplayer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.betnmoney.betsplayer.ui.adapters.ItemMatchHistory

@Database(entities = [ItemMatchHistory::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}