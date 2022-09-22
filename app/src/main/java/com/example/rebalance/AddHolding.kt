package com.example.rebalance

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rebalance.databinding.ActivityAddHoldingBinding
import com.example.rebalance.databinding.ActivitySearchBinding

class AddHolding : AppCompatActivity() {
    private lateinit var binding: ActivityAddHoldingBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddHoldingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myIntent = intent
        val derivedObject = myIntent.getSerializableExtra("investment") as Investment
        val holdingName = derivedObject.name
        val holdingCode = derivedObject.code
        val holdingPrice = derivedObject.price
        binding.investmentName.text = holdingName
        binding.investmentCode.text = holdingName
        binding.investmentPrice.text = "$"+holdingPrice.toString()

        binding.addHoldingBtn.setOnClickListener{
            val holdingUnits = binding.unitsInput.text.toString().toInt()
            val holdingAvePrice = binding.priceInput.text.toString().toFloat()
            val holdingTarget = binding.targetInput.text.toString().toDouble()
            val newHolding = Holding(
                holdingName,holdingCode,holdingUnits,holdingAvePrice,0.00,holdingPrice,holdingTarget
            )

            println(newHolding)

        }

    }
}