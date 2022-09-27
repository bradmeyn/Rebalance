package com.example.rebalance

import okhttp3.Request

data class Example(val quoteResponse: QuoteResponse)

data class QuoteResponse(val error: Any?, val result: Array<Result>)

data class Result(
    val ask: Number,
    val longName: String,
    val symbol: String,
    val ytdReturn: Number
)

data class QuoteSummary(val errorResult: ErrorResult)

data class ErrorResult(val code: String, val description: String)

