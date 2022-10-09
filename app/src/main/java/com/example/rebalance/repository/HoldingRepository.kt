package com.example.rebalance.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rebalance.database.HoldingDao
import com.example.rebalance.database.WatchItemDao
import com.example.rebalance.models.Holding
import com.example.rebalance.models.WatchItem
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

class HoldingRepository(private val holdingDao: HoldingDao) {

    val portfolio: LiveData<List<Holding>> = holdingDao.getAllHoldings()

    init {
        updateValues()
    }

    fun getItemById(id:String):LiveData<List<Holding>> {
        var list = holdingDao.getItemsById(id)
        return list
    }

    fun updateValues(){
        portfolio.value?.forEach {
                holding ->  holding.updatePrice()

        }
    }

    fun getPortfolioTotal():BigDecimal{
        var total = BigDecimal(0)
        portfolio.value?.forEach {
                holding -> total+=BigDecimal(holding.value)

        }

        return total
    }

    fun updateWeights(total: BigDecimal){
        portfolio.value?.forEach {
                holding -> holding.updateWeight(total)
        }
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(holding: Holding) {
        holdingDao.insert(holding)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(holding: Holding) {
        holdingDao.delete(holding)
    }



}