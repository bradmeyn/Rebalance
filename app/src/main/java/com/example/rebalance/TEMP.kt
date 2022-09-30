//package com.example.rebalance
//
//import android.content.Intent
//import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.annotation.RequiresApi
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.rebalance.databinding.ActivityMainBinding
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//import com.google.gson.GsonBuilder
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import okhttp3.*
//import okio.IOException
//import java.math.BigDecimal
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//import java.time.format.FormatStyle
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var appDatabase: AppDatabase
//    private lateinit var portfolio: ArrayList<Holding>
//
//    private val client by lazy {
//        OkHttpClient()
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        val myIntent = intent
//        val id = myIntent.getStringExtra("user_id")
//        val name = myIntent.getStringExtra("user_name")
//        binding.portfolioName.text = name + "'s portfolio"
//        appDatabase = AppDatabase.getDatabase(this)
//        setContentView(binding.root)
//        val current = LocalDateTime.now()
//        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
//        val formatted = current.format(formatter)
//        binding.dateText.text = formatted
//        getHoldings(id)
//        portfolio = ArrayList<Holding>()
//
//
//        binding.holdingList.layoutManager = LinearLayoutManager(this)
//        binding.holdingList.adapter = HoldingAdapter(portfolio)
//        binding.holdingList.setHasFixedSize(true)
//
//
//        binding.addHoldingBtn.setOnClickListener{
//            val intent = Intent(this, SearchActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            intent.putExtra("user_id",id)
//            intent.putExtra("user_name",name)
//            startActivity(intent)
//        }
//
//
//
//
//    }
//
//    fun buildRequest(code: String):Request {
//        return Request.Builder()
//            .url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=US&symbols=$code")
//            .get()
//            .addHeader("X-RapidAPI-Key", "3b9f8f6bd6msh7827635732b7bf6p145cc2jsn08bb77e7682c")
//            .addHeader("X-RapidAPI-Host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
//            .build()
//    }
//
//    fun getHoldings(id: String?) {
//
//        GlobalScope.launch(Dispatchers.IO){
//            if (id != null) {
//
//                if(appDatabase.userDao().getUserWithHoldings(id).isNotEmpty()) {
//
//                    portfolio = appDatabase.userDao().getUserWithHoldings(id).elementAt( 0).holdings as ArrayList<Holding>
//
//                    updateInvestments(portfolio)
//                    runOnUiThread{
//                        binding.holdingList.adapter = HoldingAdapter(portfolio)
//
//                    }
//                    println("get holdings first" +portfolio.toString())
//                } else {
//                    println("Portfolio is Empty")
//                }
//            }
//        }
//    }
//
//
//    fun updateInvestments(portfolio: ArrayList<Holding>){
//        var newPortfolio = ArrayList<Holding>()
//        var totalValue = BigDecimal(0)
//        var count = 0
//        for (holding in portfolio){
//            client.newCall(buildRequest(holding.code))
//                .enqueue(object : Callback {
//                    override fun onFailure(call: Call, e: IOException) {
//                        println("Request failed")
//                        e.printStackTrace()
//                    }
//                    override fun onResponse(call: Call, response: Response) {
//                        response.use {
//                            if (!response.isSuccessful) throw IOException("Unexpected code $response")
//                            val gson = GsonBuilder().create()
//                            val body = response.body?.string()
//                            val test = gson.fromJson(body,Example::class.java)
//                            val name = test.quoteResponse.result[0].longName
//                            val code = test.quoteResponse.result[0].symbol
//                            val price = test.quoteResponse.result[0].ask
//                            holding.updateValues(price)
//                            newPortfolio+=holding
//                            totalValue+= holding.value.toBigDecimal()
//
//
//                            runOnUiThread{
//
//                                if(count == portfolio.size -1) {
//                                    for (current in portfolio) {
//                                        current.updateWeight(totalValue)
//                                    }
//                                    binding.portfolioValue.text = "$"+totalValue.toString()
//                                    binding.holdingList.adapter = HoldingAdapter(newPortfolio)
//                                }
//
//                            }
//                            count++;
//                        }
//                    }
//
//                })
//        }
//
//
//    }
//
//
//}