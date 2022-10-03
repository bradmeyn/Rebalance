package com.example.rebalance.models

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithWatchItems(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val watchlist: List<WatchItem>
){
    private fun getUserWatchlist():List<WatchItem> {
        return watchlist
    }
}


