//package com.example.rebalance.repository
//
//import androidx.lifecycle.LiveData
//import com.example.rebalance.database.UserDao
//import com.example.rebalance.models.Holding
//
//class UserRepository(private val userDao: UserDao) {
//    val allHoldings: LiveData<List<Holding>> = userDao.getHoldings()
//
//}