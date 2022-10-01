//package com.example.rebalance
//
////import com.android.volley.Request
////import com.android.volley.Response
////import com.android.volley.toolbox.JsonObjectRequest
////import com.android.volley.toolbox.Volley
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.example.rebalance.databinding.ActivitySearchBinding
//import com.example.rebalance.models.Investment
//import com.example.rebalance.views.AddHoldingFragment
//import com.google.gson.GsonBuilder
//import okhttp3.*
//import okio.IOException
//
//class SearchActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivitySearchBinding
//    private lateinit var selectedInvestment: Investment
//
//    private var args = Bundle(1)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivitySearchBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val myIntent = intent
//        val id = myIntent.getStringExtra("user_id")
//        val name = myIntent.getStringExtra("user_name")
//
//        binding.searchBtn.setOnClickListener{
//            var inputValue = binding.searchInput.text.toString()
//            getInvestment(inputValue)
//
//        }
//
//        val newHoldingFragment = AddHoldingFragment()
//
//        binding.selectBtn.setOnClickListener{
////            val intent = Intent(this, AddHolding::class.java)
////            intent.putExtra("investment",selectedInvestment )
////            startActivity(intent)
//
//
//            args.putSerializable("investment", selectedInvestment)
//            args.putString("id",id)
//            args.putString("name",name)
//            newHoldingFragment.arguments = args
//            supportFragmentManager.beginTransaction().apply {
//            replace(R.id.searchScreen,newHoldingFragment).addToBackStack(null)
//            commit()
//        }
//        }
//    }
//
//    private fun getInvestment(query: String){
//
//        val client = OkHttpClient()
//
//            val request = Request.Builder()
//                .url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=US&symbols=$query.ax")
//                .get()
//                .addHeader("X-RapidAPI-Key", "3b9f8f6bd6msh7827635732b7bf6p145cc2jsn08bb77e7682c")
//                .addHeader("X-RapidAPI-Host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
//                .build()
//
//           client.newCall(request).enqueue(object : Callback {
//                 override fun onFailure(call: Call, e: IOException) {
//                    e.printStackTrace()
//                }
//                override fun onResponse(call: Call, response: Response) {
//                    response.use {
//                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
//
//                        val gson = GsonBuilder().create()
//                        val body = response.body?.string()
//                        println(body)
//
//                        val test = gson.fromJson(body,Example::class.java)
//                        val name = test.quoteResponse.result[0].longName
//                        val code = test.quoteResponse.result[0].symbol
//                        val price = test.quoteResponse.result[0].ask.toString()
//
//                        selectedInvestment = Investment(name, code, price)
//                       runOnUiThread{
//                           binding.investmentName.text = name
//                           binding.investmentCode.text = code
//                           binding.investmentPrice.text = "$" + price.toString()
//
//                       }
//                    }
//                }
//            })
//    }
//}
//
