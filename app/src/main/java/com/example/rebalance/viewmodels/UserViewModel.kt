package com.example.rebalance.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.room.ColumnInfo
import com.example.rebalance.database.AppDatabase
import com.example.rebalance.models.Holding
import com.example.rebalance.models.Investment
import com.example.rebalance.models.WatchItem
import kotlinx.coroutines.launch
import java.math.BigDecimal

class UserViewModel(app: Application): AndroidViewModel(app) {

    private var _id = MutableLiveData("")
    private var _username = MutableLiveData("")
    private var _selectedInvestment = MutableLiveData<Investment>()
    private var _portfolioValue = MutableLiveData("0")
    var tempHolding = Holding(1, "userId","Fund 1", "ONE", 100,"20.00","10", "32.00","20","10000")
    var tempHolding2 = Holding(2, "userId","Fund 2", "Two", 100,"20.00","10", "32.00","20","10000")
    private val _portfolio: MutableLiveData<ArrayList<Holding>> = MutableLiveData(arrayListOf(tempHolding,tempHolding2))

    var watchItem1 = WatchItem("VAS.AX", "userID","Vanguard AU", "89.55", "83.00")
    var watchItem2 = WatchItem("VGS.AX", "userID","Vanguard Int", "89.55", "83.00")

    private val _watchlist: MutableLiveData<ArrayList<WatchItem>> = MutableLiveData(arrayListOf(watchItem1,watchItem2))

    val id: LiveData<String> = _id
    val username: LiveData<String> = _username
    val selectedInvestment:LiveData<Investment> = _selectedInvestment
    val portfolio: LiveData<ArrayList<Holding>> = _portfolio
    val watchlist:LiveData<ArrayList<WatchItem>> = _watchlist
    val portfolioValue: LiveData<String> = _portfolioValue


    suspend fun getWatchlist(){

        val userDao = AppDatabase.getDatabase((getApplication()))?.userDao()
           _watchlist.value =  userDao?.getUserWithWatchlist(id.toString()).elementAt( 0).watchlist as ArrayList<WatchItem>
    }

    fun addHolding(newHolding: Holding){

        _portfolio.value?.add(newHolding)

//        getPortfolio()
    }

    suspend fun addWatchItem(investment: WatchItem){
        val userDao = AppDatabase.getDatabase((getApplication()))?.userDao()
        userDao?.addWatchItem(investment)
        getWatchlist()
    }


    //get prices, update holding value
    // calculate total value
    //calculate weights

    fun updatePortfolioPrices(){
        //update prices in the
    }

    fun setPortfolioTotals() {
        var total = BigDecimal(0)
        var updatedPortfolio = _portfolioValue.value
        for (investment in _portfolio.value!!) {
            updatedPortfolio
            total += BigDecimal(investment.units) * BigDecimal(investment.curPrice)
        }
        _portfolioValue.value = total.toString()
    }

//    fun calculatePortfolioValues(){
//        for (investment in _portfolio.value!!){
//            total+= BigDecimal(investment.units) * BigDecimal(investment.curPrice)
//        }
//    }


    fun isInPortfolio(code: String):Boolean{


    var isMatch = false

        for (investment in _portfolio.value!!){

            if(code.equals(investment.code)){

            isMatch = true}

        }

        return isMatch
    }

    fun isInWatchlist(code: String):Boolean{

        return true
    }

    fun getPortfolio(){

    }

    fun removeHolding(code: String){

        getPortfolio()
    }

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



//@ColumnInfo
//val userId: String,
//@ColumnInfo
//val name:String,
//@ColumnInfo
//val code:String,
//@ColumnInfo
//val units:Int,
//@ColumnInfo
//val avePrice: String,
//@ColumnInfo
//val targetWeight: String,
//@ColumnInfo
//var curPrice: String,
//@ColumnInfo
//var currentWeight: String,
//@ColumnInfo
//var value: String
//):Serializable{