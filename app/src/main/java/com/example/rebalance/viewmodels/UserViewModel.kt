package com.example.rebalance.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.room.ColumnInfo
import com.example.rebalance.database.AppDatabase
import com.example.rebalance.models.Holding
import com.example.rebalance.models.Investment
import com.example.rebalance.models.WatchItem

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal

class UserViewModel(application: Application): AndroidViewModel(application) {

    private var _id = MutableLiveData("")
    private var _username = MutableLiveData("")
    private var _selectedInvestment = MutableLiveData<Investment>()


    val id: LiveData<String> = _id
    val username: LiveData<String> = _username
    val selectedInvestment:LiveData<Investment> = _selectedInvestment

    fun setId( newId: String){
        _id.value = newId
    }

    fun setSelectedInvestment(investment: Investment){
        _selectedInvestment.value = investment
    }

    fun setUsername( newUsername: String){
        _username.value = newUsername
    }
}

