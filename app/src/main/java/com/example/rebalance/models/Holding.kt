package com.example.rebalance.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.math.BigDecimal

@Entity
data class Holding(
    @PrimaryKey(autoGenerate = true) val holdingId:Int? = 0,
    @ColumnInfo val userId: String,
    @ColumnInfo val name:String,
    @ColumnInfo val code:String,
    @ColumnInfo val units:Int,
    @ColumnInfo val avePrice: String,
    @ColumnInfo val targetWeight: String,
    @ColumnInfo var curPrice: String,
    @ColumnInfo var currentWeight: String,
    @ColumnInfo var value: String
    ):Serializable{


        fun updateValues(newPrice: Number){
            curPrice = newPrice.toString()
            value = (newPrice.toString().toBigDecimal() * units.toBigDecimal()).toString()
        }

        fun updateWeight(totalBalance: BigDecimal){
            currentWeight = ((value.toBigDecimal()/totalBalance) * BigDecimal(100)).toString()
        }
    }
