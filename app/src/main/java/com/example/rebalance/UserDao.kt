package com.example.rebalance

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUser(userId: String):User


    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun addHolding(holding: Holding)

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUserWithHoldings(userId: String):List<UserWithHoldings>

    @Delete
    suspend fun delete(user: User)
}