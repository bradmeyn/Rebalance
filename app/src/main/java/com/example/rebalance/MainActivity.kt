package com.example.rebalance

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rebalance.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var portfolio: ArrayList<Holding>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val myIntent = intent
        val id = myIntent.getStringExtra("user_id")
        val name = myIntent.getStringExtra("user_name")
        binding.portfolioName.text = name + "'s portfolio"
        appDatabase = AppDatabase.getDatabase(this)
        setContentView(binding.root)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)
        binding.dateText.text = formatted

        println(id)

        getHoldings(id)

//        var newHolding = Holding(2,"bread","test","code",4,"$50","$40","10%")
        portfolio = ArrayList<Holding>()
//        portfolio+=newHolding
//        investmentList = ArrayList<Investment>()
//        val inv1 = Investment("Vanguard Aus", "VAS", (100).toBigDecimal())
//        investmentList += inv1
//        val inv2 = Investment("Vanguard Int", "VGS", (90.50).toBigDecimal())
//        investmentList+= inv2

        binding.holdingList.layoutManager = LinearLayoutManager(this)
        binding.holdingList.setHasFixedSize(true)
        binding.holdingList.adapter = HoldingAdapter(portfolio)

        binding.addHoldingBtn.setOnClickListener{
            val intent = Intent(this, SearchActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("user_id",id)
            intent.putExtra("user_name",name)
            startActivity(intent)
        }
    }



    private fun getHoldings(id: String?) {


        GlobalScope.launch(Dispatchers.IO){
            if (id != null) {

                if(appDatabase.userDao().getUserWithHoldings(id).isNotEmpty()) {
                    println("searching db for user")
                    portfolio = appDatabase.userDao().getUserWithHoldings(id).elementAt( 0).holdings as ArrayList<Holding>
                    binding.holdingList.adapter = HoldingAdapter(portfolio)
                    println("get holdings first" +portfolio.toString())
                } else {
                    println("Portfolio is Empty")
                }


            }
            }


    }
}