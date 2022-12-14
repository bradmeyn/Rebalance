package com.example.rebalance.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class User(
    @PrimaryKey @NotNull val userId: String = "noId",
    @ColumnInfo(name = "name") val name: String?,

    )

