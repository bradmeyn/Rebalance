package com.example.rebalance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
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
    @ColumnInfo val lastPrice: String,
    @ColumnInfo val targetWeight: String
    ):Serializable{}
