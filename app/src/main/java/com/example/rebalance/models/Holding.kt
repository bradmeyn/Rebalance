package com.example.rebalance.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import java.math.BigDecimal

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
            println("Current Weight " + currentWeight)
            println("Target Weight " + targetWeight)
            if((BigDecimal(currentWeight) - BigDecimal(targetWeight)).abs()> BigDecimal(5)){
                isBalanced = false
            }
            return isBalanced
        }

        fun updateWeight(totalBalance: BigDecimal){
            currentWeight = ((value.toBigDecimal()/totalBalance) * BigDecimal(100)).toString()
        }
    }
