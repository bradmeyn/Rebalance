package com.example.rebalance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rebalance.databinding.ActivityMainBinding
import com.example.rebalance.databinding.ActivitySearchBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var investmentList: ArrayList<Investment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        investmentList = ArrayList<Investment>()
//
        val inv1 = Investment("Vanguard Aus", "VAS", (100).toFloat())
        investmentList += inv1
        val inv2 = Investment("Vanguard Int", "VGS", (90.50).toFloat())
        investmentList+= inv2

        binding.holdingList.adapter = InvestmentAdapter(investmentList)
        binding.holdingList.layoutManager = LinearLayoutManager(this)
        binding.holdingList.setHasFixedSize(true)
    }
}