package com.example.rebalance.api

import com.squareup.moshi.Json


data class Market (
    @Json(name="shortName")
    val name: String? = null,
//    val prevClose: String,
    @Json(name="marketState")
    val marketState: String,
        )

data class MarketResult  (
    @Json(name="result")
    val result: List<Market>
)

data class MarketResponse(
    @Json(name="marketSummaryAndSparkResponse")
    val result: MarketResult
)