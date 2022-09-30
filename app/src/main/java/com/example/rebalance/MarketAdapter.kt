package com.example.rebalance

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.recyclerview.widget.RecyclerView
import com.example.rebalance.api.Quote
import java.math.MathContext
import java.math.RoundingMode

class MarketAdapter(private val markets: List<Quote>) : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.investment_rv_item, parent, false)

        return MarketViewHolder(itemView)

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {

        val market = markets[position]

       holder.title.text = market.title
        holder.name.text = market.shortName
        holder.change.text = market.oneDayChange.toBigDecimal().round(MathContext(3, RoundingMode.HALF_UP)).toString() + "%"
        if(market.oneDayChange.toDouble() <0){
            holder.change.setTextColor(Color.parseColor("#a61111"))

        }
        if(market.oneDayChange.toDouble() > 0){
            holder.change.setTextColor(Color.parseColor("#489361"))

        }

        holder.value.text = market.price

        val drawableID: Int = holder.img.context.getResources().getIdentifier(market.img, "drawable", holder.img.context.getPackageName())
        holder.img.setImageResource(drawableID)


    }

    override fun getItemCount() = markets.size

    class MarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.marketTitle)
        val name: TextView = itemView.findViewById(R.id.marketName)
        val change: TextView = itemView.findViewById(R.id.marketChange)
        val value: TextView = itemView.findViewById(R.id.marketValue)
        val img: ImageView = itemView.findViewById(R.id.flag)
    }
}