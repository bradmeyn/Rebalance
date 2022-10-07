package com.example.rebalance.database

import androidx.lifecycle.LiveData
import com.example.rebalance.models.WatchItem
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchItemDao {


    @Query("SELECT * FROM watchitem")
    fun getAllWatchItems(): LiveData<List<WatchItem>>

    @Transaction
    @Query("SELECT * FROM watchitem WHERE userId = :id")
    fun getItemsById(id: String): LiveData<List<WatchItem>>
//
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insert(watchItem: WatchItem)
//
//
//    @Transaction
//    @Query("SELECT * FROM user WHERE userId = :userId")
//    suspend fun getUserWithHoldings(userId: String):List<UserWithHoldings>
//
    @Delete
    suspend fun delete(watchItem: WatchItem)

    @Query("DELETE FROM watchitem")
    suspend fun deleteAll()
}