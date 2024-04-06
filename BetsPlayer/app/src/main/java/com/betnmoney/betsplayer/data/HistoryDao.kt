package com.betnmoney.betsplayer.data

import androidx.room.*
import com.betnmoney.betsplayer.ui.adapters.ItemMatchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemMatchHistory: ItemMatchHistory)

    @Delete
    suspend fun delete(itemMatchHistory: ItemMatchHistory)

    @Query("SELECT * FROM history")
    fun fetchHistory(): Flow<List<ItemMatchHistory>>
}