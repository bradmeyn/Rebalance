package com.example.rebalance.adapters


import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.rebalance.models.Holding
import com.example.rebalance.R

class HoldingAdapter(private val investments: List<Holding>) : RecyclerView.Adapter<HoldingAdapter.HoldingViewHolder>() {

//    How many items the adapter needs to display
    override fun getItemCount() = investments.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.holding_rv_item, parent, false)
        return HoldingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HoldingViewHolder, position: Int) {

        val currentHolding = investments[position]

        holder.apply {
            weight.text = currentHolding.currentWeight+"%"
            code.text = currentHolding.code
            units.text = currentHolding.units.toString()
            price.text = "$"+currentHolding.curPrice
            value.text = "$"+currentHolding.value
        }



    }


    class HoldingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        UI items
        val units: TextView = itemView.findViewById(R.id.holdingUnits)
        val code: TextView = itemView.findViewById(R.id.holdingCode)
        val price: TextView = itemView.findViewById(R.id.holdingPrice)
        val value: TextView = itemView.findViewById(R.id.holdingValue)
        val weight: TextView = itemView.findViewById(R.id.holdingWeight)
    }


}

