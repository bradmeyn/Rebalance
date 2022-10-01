package com.example.rebalance.database

import androidx.room.*
import com.example.rebalance.models.Holding

@Dao
interface HoldingDao {
//    @Query("SELECT * FROM user")
//    fun getAllUsers(): List<User>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE )
//    suspend fun addUser(user: User)
//
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun addHolding(holding: Holding)
//
//    @Transaction
//    @Query("SELECT * FROM user WHERE userId = :userId")
//    suspend fun getUserWithHoldings(userId: String):List<UserWithHoldings>
//
//    @Delete
//    suspend fun delete(user: User)
}