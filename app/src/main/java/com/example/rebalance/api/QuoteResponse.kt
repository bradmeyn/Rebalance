package com.example.rebalance.api


import com.squareup.moshi.Json

data class QuoteResponse(
    @Json(name="quoteResponse")
    val quoteResult: QuoteResult
)

data class QuoteResult (
    @Json(name="result")
    val result: List<Quote>
)

data class Quote (
    @Json(name="shortName")
    val shortName: String? = null,
    @Json(name="longName")
    val longName: String? = null,
    @Json(name="marketState")
    val marketState: String,
    @Json(name="symbol")
    val symbol: String,
    @Json(name="regularMarketPrice")
    val price: String,
    @Json(name="regularMarketChangePercent")
    val oneDayChange: String,
    var img:String? = null,
    var title:String? = null


)