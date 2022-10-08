package com.example.rebalance.models


import java.io.Serializable
import java.math.BigDecimal

data class Investment(val name:String,
                      val code:String,
                      val price:String,
                      val units:Int = 0,
                      val avePrice:String = "",
                      val target:String = ""
): Serializable
{

}
