package com.example.rebalance.models

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rebalance.api.ApiClient
import com.example.rebalance.api.QuoteResponse
import org.jetbrains.annotations.NotNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

@Entity
data class Holding(

    @PrimaryKey @NotNull val code:String,
    @ColumnInfo val userId: String,
    @ColumnInfo val name:String,
    @ColumnInfo val units:Int,
    @ColumnInfo val avePrice: String,
    @ColumnInfo val targetWeight: String,
    @ColumnInfo var curPrice: String,
    @ColumnInfo var currentWeight: String,
    @ColumnInfo var value: String
    ):Serializable{

        fun calculateCostbase():BigDecimal{
            return avePrice.toBigDecimal() * units.toBigDecimal()
        }

        fun calculateCurrentValue():BigDecimal{
            return curPrice.toBigDecimal() * units.toBigDecimal()
        }

        fun calculateDollarReturn():BigDecimal {
            val costbase = calculateCostbase()
            val value = calculateCurrentValue()
            return value - costbase
        }

        fun calculatePercentageReturn():BigDecimal {
            val costbase = calculateCostbase()
            val value = calculateCurrentValue()
            return ((value - costbase) / costbase) * BigDecimal(100)
        }

        fun isBalanced():Boolean{
            var isBalanced = true
            if((BigDecimal(currentWeight) - BigDecimal(targetWeight)).abs()> BigDecimal(5)){
                isBalanced = false
            }
            return isBalanced
        }

        fun updateWeight(totalBalance: BigDecimal){
            currentWeight = ((value.toBigDecimal()/totalBalance) * BigDecimal(100)).round(
                MathContext(3, RoundingMode.HALF_UP)
            ).toString()
        }

    fun updatePrice(){
        val client = ApiClient.apiService.fetchQuotes("AU", code)
        client.enqueue(object : Callback<QuoteResponse> {

            override fun onResponse(
                call: Call<QuoteResponse>,
                response: Response<QuoteResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("response", response.body().toString())

                    var quotes = response.body()?.quoteResult
                    quotes?.let {

                        if(quotes.result.size > 0){

                            var investment = quotes.result[0]

                            curPrice =  investment.price
                            value = calculateCurrentValue().toString()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<QuoteResponse>, t: Throwable) {
                Log.e("Failure:", ""+t.message)
            }
        })


            }


    }
