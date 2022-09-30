package com.example.rebalance.api

import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.text.DateFormatSymbols

object ApiClient {

    private val URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/"
//     "?region=AU/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit:Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}

interface ApiService {
    @Headers("X-RapidAPI-Key: 3b9f8f6bd6msh7827635732b7bf6p145cc2jsn08bb77e7682c", "X-RapidAPI-Host: apidojo-yahoo-finance-v1.p.rapidapi.com" )
    @GET("market/v2/get-quotes")
    fun fetchQuotes(
        @Query("region") region:String,
        @Query("symbols") symbols: String):Call<QuoteResponse>
}