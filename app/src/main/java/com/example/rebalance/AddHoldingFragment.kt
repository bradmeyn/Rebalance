package com.example.rebalance

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddHoldingFragment : Fragment(R.layout.fragment_add_holding) {


    lateinit var textName:TextView
    lateinit var textCode:TextView
    lateinit var textPrice:TextView
    lateinit var addBtn:Button
    lateinit var passedInvestment: Investment
    private lateinit var appDatabase: AppDatabase
    private lateinit var id: String
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

//        val derivedObject = myIntent.getSerializableExtra("investment") as Investment
////        val holdingName = derivedObject.name
////        val holdingCode = derivedObject.code
        }
//
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    var context = activity
    appDatabase = AppDatabase.getDatabase(context!!)
    // Inflate the layout for this fragment
    var view =  inflater.inflate(R.layout.fragment_add_holding, container, false)

    if(arguments != null){
       passedInvestment = requireArguments().getSerializable("investment") as Investment
        id = requireArguments().getString("id").toString()
        name = requireArguments().getString("name").toString()
        textName = view.findViewById(R.id.investmentName)
        textCode = view.findViewById(R.id.investmentCode)
        textPrice = view.findViewById(R.id.investmentPrice)
        textName.text = passedInvestment.name
        textCode.text = passedInvestment.code
        textPrice.text = passedInvestment.price.toString()
    }

        addBtn = view.findViewById<Button>(R.id.addHoldingBtn)
        var unitsInput = view.findViewById<TextInputEditText>(R.id.unitsInput)
        var targetInput = view.findViewById<TextInputEditText>(R.id.targetInput)
        var priceInput = view.findViewById<TextInputEditText>(R.id.priceInput)

        addBtn.setOnClickListener{

            var newHolding = Holding(
                null,
                id,
                passedInvestment.name,
                passedInvestment.code,
                unitsInput.text.toString().toInt(),
                priceInput.text.toString(),
                targetInput.text.toString(),
                passedInvestment.price,
                "0",
                "0"
            )
            saveHolding(newHolding)

            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("user_id",id)
            intent.putExtra("user_name",name)
            startActivity(intent)
//            activity?.supportFragmentManager?.popBackStack()
        }
        return view

    }

    private fun saveHolding(newHolding: Holding){
        GlobalScope.launch(Dispatchers.IO){
            appDatabase.holdingDao().addHolding(newHolding)
        }
    }



}