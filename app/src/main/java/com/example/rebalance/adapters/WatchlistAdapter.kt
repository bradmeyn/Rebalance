package com.example.rebalance.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.rebalance.R
import com.example.rebalance.api.ApiClient
import com.example.rebalance.api.QuoteResponse
import com.example.rebalance.models.Investment
import com.example.rebalance.models.WatchItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.MathContext
import java.math.RoundingMode

class WatchlistAdapter(private val watchlist: List<WatchItem>) : RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.watchlist_rv, parent, false)

        return WatchlistViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {

        val currentItem = watchlist[position]


        holder.code.text = currentItem.code
//        holder.price.text = "$"+ currentItem.price
//        holder.change.text =
        holder.name.text = currentItem.name
        holder.target.text = "Target: $" + currentItem.targetPrice

        val client = ApiClient.apiService.fetchQuotes("AU", currentItem.code)
        client.enqueue(object : Callback<QuoteResponse> {

            override fun onResponse(
                call: Call<QuoteResponse>,
                response: Response<QuoteResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("response", response.body().toString())
                    println("success")
                    var quotes = response.body()?.quoteResult
                    quotes?.let {
                        println(quotes)
                        var investment = quotes.result[0]

                        holder.price.text = "$" + investment.price
                        holder.change.text = investment.oneDayChange.toBigDecimal().round(
                            MathContext(3, RoundingMode.HALF_UP)
                        ).toString() + "%"
                        if(investment.oneDayChange.toDouble() <0){
                            holder.change.setTextColor(Color.parseColor("#a61111"))
                        }
                        if(investment.oneDayChange.toDouble() > 0){
                            holder.change.setTextColor(Color.parseColor("#489361"))
                        }
                    }
                }

            }

            override fun onFailure(call: Call<QuoteResponse>, t: Throwable) {
                Log.e("Failure:", ""+t.message)
            }


        })


    }

    override fun getItemCount() = watchlist.size

    class WatchlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val code: TextView = itemView.findViewById(R.id.investmentCode)
        val price: TextView = itemView.findViewById(R.id.investmentPrice)
        val change: TextView = itemView.findViewById(R.id.investmentDayChange)
        val name: TextView = itemView.findViewById(R.id.investmentName)
        val target: TextView = itemView.findViewById(R.id.targetPrice)
    }
}