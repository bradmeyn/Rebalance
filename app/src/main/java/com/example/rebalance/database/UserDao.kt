package com.example.rebalance.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.rebalance.models.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUser(userId: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun addHolding(holding: Holding)

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUserWithHoldings(userId: String):List<UserWithHoldings>

//    Watchlist
    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUserWithWatchlist(userId: String): LiveData<List<UserWithWatchItems>>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertWatchItem(investment: WatchItem)

    @Delete
    suspend fun deleteWatchItem(investment: WatchItem)

    @Delete
    suspend fun delete(user: User)
}