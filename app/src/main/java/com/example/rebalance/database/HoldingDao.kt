package com.example.rebalance.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.rebalance.models.Holding

@Dao
interface HoldingDao {
    @Query("SELECT * FROM holding")
    fun getAllHoldings(): LiveData<List<Holding>>

    @Transaction
    @Query("SELECT * FROM holding WHERE userId = :id")
    fun getItemsById(id: String): LiveData<List<Holding>>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insert(holding: Holding)

//    @Transaction
//    @Query("SELECT * FROM user WHERE userId = :userId")
//    suspend fun getUserWithHoldings(userId: String):List<UserWithHoldings>
//
    @Delete
    suspend fun delete(holding: Holding)

    @Query("DELETE FROM holding")
    suspend fun deleteAll()
}