//package com.example.rebalance.repository
//
//import android.app.Application
//import androidx.annotation.WorkerThread
//import androidx.lifecycle.LiveData
//import com.example.rebalance.database.AppDatabase
//import com.example.rebalance.database.UserDao
//import com.example.rebalance.models.Holding
//import com.example.rebalance.models.UserWithWatchItems
//import com.example.rebalance.models.WatchItem
//import java.util.concurrent.Flow
//
//class UserRepository(application: Application) {
//
//    private var db = AppDatabase.getDatabase(application)
//
//    val userWithPortfolio: kotlinx.coroutines.flow.Flow<List<UserWithWatchItems>> = userDao.getUserWithWatchlist()
//
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun insertWatchItem(investment: WatchItem) = userDao.insertWatchItem(investment)
//
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun deleteWatchItem(investment: WatchItem) = userDao.deleteWatchItem(investment)
//
//    fun getUserWithWatchlist(userId: String) = userDao.getUserWithWatchlist(userId)
//}