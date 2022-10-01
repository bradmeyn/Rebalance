package com.example.rebalance.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {

    private var _id = MutableLiveData("")
    private var _username = MutableLiveData("")
    val id: LiveData<String> = _id
    val username: LiveData<String> = _username

//    val watchlist: MutableLiveData<Array<>>

    fun setId( newId: String){
        _id.value = newId
    }

    fun setUsername( newUsername: String){
        _username.value = newUsername
    }
}