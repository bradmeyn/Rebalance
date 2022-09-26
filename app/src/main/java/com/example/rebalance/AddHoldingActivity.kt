package com.example.rebalance

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rebalance.databinding.ActivityAddHoldingBinding
import com.example.rebalance.databinding.ActivitySearchBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddHoldingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddHoldingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityAddHoldingBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_add_holding)

//        val searchFragment = SearchFragment()

//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragmentFrame,searchFragment)
//            commit()
//        }


//
//        val myIntent = intent
//        val derivedObject = myIntent.getSerializableExtra("investment") as Investment
//        val holdingName = derivedObject.name
//        val holdingCode = derivedObject.code
//        val holdingPrice = derivedObject.price.toString()
//        val userId = Firebase.auth.currentUser?.uid.toString()
//        binding.investmentName.text = holdingName
//        binding.investmentCode.text = holdingName
//        binding.investmentPrice.text = "$" + holdingPrice
//
//        binding.addHoldingBtn.setOnClickListener{
//            val holdingUnits = binding.unitsInput.text.toString().toInt()
//            val holdingAvePrice = binding.priceInput.text.toString()
//            val holdingTarget = binding.targetInput.text.toString()
//            val newHolding = Holding(
//                null,userId,holdingName,holdingCode,holdingUnits,holdingAvePrice,holdingPrice,holdingTarget
//            )

//            println(newHolding.toString())

        }

    }
