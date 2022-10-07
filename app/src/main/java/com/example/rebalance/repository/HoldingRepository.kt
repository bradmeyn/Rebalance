package com.example.rebalance.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.rebalance.database.HoldingDao
import com.example.rebalance.database.WatchItemDao
import com.example.rebalance.models.Holding
import com.example.rebalance.models.WatchItem
import kotlinx.coroutines.flow.Flow

class HoldingRepository(private val holdingDao: HoldingDao) {

    val portfolio: LiveData<List<Holding>> = holdingDao.getAllHoldings()

    fun getItemById(id:String):LiveData<List<Holding>> {
        var list = holdingDao.getItemsById(id)
        return list
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(holding: Holding) {
        holdingDao.insert(holding)
    }



}