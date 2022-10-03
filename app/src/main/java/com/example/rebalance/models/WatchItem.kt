package com.example.rebalance.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import java.math.BigDecimal

@Entity
data class WatchItem(
    @PrimaryKey @NotNull val code:String,
    @ColumnInfo val userId: String,
    @ColumnInfo val name:String,
    @ColumnInfo val price: String,
    @ColumnInfo val targetPrice: String,

    ):Serializable{


}
