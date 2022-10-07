package com.example.rebalance.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.rebalance.database.WatchItemDao
import com.example.rebalance.models.WatchItem
import kotlinx.coroutines.flow.Flow

class WatchItemRepository(private val watchItemDao: WatchItemDao) {

    val watchlist: LiveData<List<WatchItem>> = watchItemDao.getAllWatchItems()

    fun getItemById(id:String):LiveData<List<WatchItem>> {
        var list = watchItemDao.getItemsById(id)
        return list
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(watchItem: WatchItem) {
        watchItemDao.insert(watchItem)
    }



}