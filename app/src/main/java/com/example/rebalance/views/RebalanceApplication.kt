package com.example.rebalance.views

import android.app.Application
import com.example.rebalance.database.AppDatabase
import com.example.rebalance.repository.HoldingRepository
import com.example.rebalance.repository.WatchItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class RebalanceApplication: Application() {
 val database by lazy {AppDatabase.getDatabase(this, MainScope() )}
    val watchlistRepo by lazy {WatchItemRepository(database.watchItemDao())}
    val portfolioRepo by lazy {HoldingRepository(database.holdingDao())}
}