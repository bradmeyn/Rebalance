package com.example.rebalance

import java.io.Serializable

data class Holding(
    val name:String,
    val code:String,
    val units:Int,
    val avePrice: Float,
    val weighting: Double,
    val curPrice: Float,
    val targetWeight: Double
    ):Serializable{}
