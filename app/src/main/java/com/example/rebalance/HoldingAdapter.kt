package com.example.rebalance


import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView

class HoldingAdapter(private val investments: List<Holding>) : RecyclerView.Adapter<HoldingAdapter.HoldingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldingViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.holding_rv_item, parent, false)

        return HoldingViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: HoldingViewHolder, position: Int) {

        val currentItem = investments[position]

        holder.invName.text = currentItem.name
        holder.invCode.text = currentItem.code
        holder.invPrice.text = "$"+ currentItem.lastPrice


    }

    override fun getItemCount() = investments.size

    class HoldingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val invName: TextView = itemView.findViewById(R.id.holdingName)
        val invCode: TextView = itemView.findViewById(R.id.holdingCode)
        val invPrice: TextView = itemView.findViewById(R.id.holdingPrice)
    }
}