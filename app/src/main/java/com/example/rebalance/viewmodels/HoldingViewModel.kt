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

//    val _portfolio: MutableLiveData<Holding> = portfolio
//    lateinit var portfolioTotal: MutableLiveData<BigDecimal>



    fun insert(holding: Holding) = viewModelScope.launch {
        repository.insert(holding)
    }



//       fun calculatePortfolioValues():BigDecimal{
//           var total = BigDecimal(0)
//        for (investment in portfolio.value!!){
//            total+= BigDecimal(investment.units) * BigDecimal(investment.curPrice)
//        }
//           return total
//    }

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
