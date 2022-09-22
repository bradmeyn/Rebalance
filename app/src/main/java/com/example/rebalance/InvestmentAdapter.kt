package com.example.rebalance

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView

class InvestmentAdapter(private val investments: List<Investment>) : RecyclerView.Adapter<InvestmentAdapter.InvestmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvestmentViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.investment_rv_item, parent, false)

        return InvestmentViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: InvestmentViewHolder, position: Int) {

        val currentItem = investments[position]

        holder.invName.text = currentItem.name
        holder.invCode.text = currentItem.code
        holder.invPrice.text = "$"+ currentItem.price.toString()


    }

    override fun getItemCount() = investments.size

    class InvestmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val invName: TextView = itemView.findViewById(R.id.investmentName)
        val invCode: TextView = itemView.findViewById(R.id.investmentCode)
        val invPrice: TextView = itemView.findViewById(R.id.investmentPrice)
    }
}