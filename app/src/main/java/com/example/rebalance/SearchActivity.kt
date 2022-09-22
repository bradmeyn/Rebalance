package com.example.rebalance

//import com.android.volley.Request
//import com.android.volley.Response
//import com.android.volley.toolbox.JsonObjectRequest
//import com.android.volley.toolbox.Volley
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rebalance.databinding.ActivitySearchBinding
import com.google.gson.GsonBuilder
import okhttp3.*
import okio.IOException

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var selectedInvestment: Investment
    private lateinit var investmentList: ArrayList<Investment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        investmentList = ArrayList<Investment>()
//        val testList = ArrayList<Investment>()
//
//        val inv1 = Investment("Vanguard Aus", "VAS", 90)
//        testList += inv1
//        val inv2 = Investment("Vanguard Int", "VGS", 200)

//        testList+= inv2
//        print(testList)
//        println(testList)
//        binding.investmentList.adapter = InvestmentAdapter(testList)
        binding.investmentList.layoutManager = LinearLayoutManager(this)
        binding.investmentList.setHasFixedSize(true)
        binding.searchBtn.setOnClickListener{

            var inputValue = binding.searchInput.text.toString()
            getInvestments(inputValue)


//            binding.investmentList.adapter = InvestmentAdapter(testList)
            binding.investmentList.layoutManager = LinearLayoutManager(this)
            binding.investmentList.setHasFixedSize(true)
        }

        binding.selectBtn.setOnClickListener{
            val intent = Intent(this, AddHolding::class.java)
            intent.putExtra("investment",selectedInvestment )
            startActivity(intent)
        }


    }

    private  fun getInvestments(query: String){
        println(query)


        val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=US&symbols=$query.ax")
                .get()
                .addHeader("X-RapidAPI-Key", "3b9f8f6bd6msh7827635732b7bf6p145cc2jsn08bb77e7682c")
                .addHeader("X-RapidAPI-Host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .build()

           client.newCall(request).enqueue(object : Callback {
                 override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")

//                        for ((name, value) in response.headers) {
//                            println("$name: $value")
//                        }
                        val gson = GsonBuilder().create()
                        val body = response.body?.string()
                        println(body)

                        val test = gson.fromJson(body,Example::class.java)
                        val name = test.quoteResponse.result[0].longName.toString()
                        val code = test.quoteResponse.result[0].symbol.toString()
                        val price = test.quoteResponse.result[0].ask.toFloat()

                        selectedInvestment = Investment(name, code, price)
                       runOnUiThread{
                           investmentList += selectedInvestment
                           binding.investmentList.adapter = InvestmentAdapter(investmentList)
                           binding.testName.text = selectedInvestment.name
                       }

                    }
                }
            })

    }
}

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