package com.example.rebalance

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithHoldings(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val holdings: List<Holding>
){
    private fun giveHoldings():List<Holding> {
        return this.holdings
    }
}


