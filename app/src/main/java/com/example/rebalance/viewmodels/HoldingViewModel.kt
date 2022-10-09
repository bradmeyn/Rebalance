package com.example.rebalance.viewmodels

import androidx.lifecycle.*
import com.example.rebalance.models.Holding
import com.example.rebalance.models.WatchItem
import com.example.rebalance.repository.HoldingRepository
import com.example.rebalance.repository.WatchItemRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal

class HoldingViewModel(private val repository: HoldingRepository):ViewModel() {

    val portfolio: LiveData<List<Holding>> = repository.portfolio

    fun insert(holding: Holding) = viewModelScope.launch {
        repository.insert(holding)
    }

    fun delete(item: Holding) = viewModelScope.launch {
        repository.delete(item)
    }

    fun updateValues(){
        repository.updateValues()
    }

    fun updateValues(total:BigDecimal){
        repository.updateWeights(total)
    }

    fun getPortfolioTotal():BigDecimal{
        return repository.getPortfolioTotal()
    }
}


class HoldingViewModelFactory(private val repository: HoldingRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HoldingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HoldingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
