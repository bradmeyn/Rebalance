package com.example.rebalance.viewmodels

import androidx.lifecycle.*
import com.example.rebalance.models.WatchItem
import com.example.rebalance.repository.WatchItemRepository
import kotlinx.coroutines.launch

class WatchItemViewModel(private val repository: WatchItemRepository):ViewModel() {

    val watchlist: LiveData<List<WatchItem>> = repository.watchlist

    fun insert(item: WatchItem) = viewModelScope.launch {
        repository.insert(item)
    }

    fun delete(item: WatchItem) = viewModelScope.launch {
        repository.delete(item)
    }

}


class WatchItemViewModelFactory(private val repository: WatchItemRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WatchItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WatchItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
