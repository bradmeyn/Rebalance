package com.example.rebalance.views

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.rebalance.R
import com.example.rebalance.viewmodels.UserViewModel
import com.example.rebalance.api.ApiClient
import com.example.rebalance.api.QuoteResponse
import com.example.rebalance.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.MathContext
import java.math.RoundingMode


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.searchBtn.setOnClickListener {
            var query = binding.searchInput.text.toString()
            var auBtn = binding.auRadioBtn.isChecked


            if(isValidateSearch(query)){
                if(auBtn) {
                    query+= ".AX"
                }
                println(query)
                searchInvestment(query)
            }
        }



        return binding.root
    }

    fun searchInvestment(code: String){
        val client = ApiClient.apiService.fetchQuotes("AU", code)
        client.enqueue(object : Callback<QuoteResponse> {

            override fun onResponse(
                call: Call<QuoteResponse>,
                response: Response<QuoteResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("response", response.body().toString())
                    println("success")
                    var quotes = response.body()?.quoteResult
                    quotes?.let {
                        println(quotes)
                        var investment = quotes.result[0]
                        binding.marketCardAus.visibility = android.view.View.VISIBLE
                        binding.investmentName.text= investment.longName.toString()
                        binding.investmentCode.text= investment.symbol
                        binding.investmentPrice.text = "$" + investment.price
                        binding.investmentChange.text= investment.oneDayChange.toBigDecimal().round(MathContext(3, RoundingMode.HALF_UP)).toString() + "%"
                        if(investment.oneDayChange.toDouble() <0){
                            binding.investmentChange.setTextColor(Color.parseColor("#a61111"))
                        }
                        if(investment.oneDayChange.toDouble() > 0){
                            binding.investmentChange.setTextColor(Color.parseColor("#489361"))
                        }
                        }
                    }

                }

            override fun onFailure(call: Call<QuoteResponse>, t: Throwable) {
                Log.e("Failure:", ""+t.message)
            }


        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.portfolioBtn.setOnClickListener{
            navController!!.navigate(R.id.action_searchFragment_to_addHoldingFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun isValidateSearch(query:String):Boolean{

        var validSearch = true
        var context = activity
        if(query.isEmpty()) {
            binding.searchInput.setError("Search cannot be empty")
            Toast.makeText(
                context, "Search is empty.",
                Toast.LENGTH_SHORT).show()
            validSearch = false
        }

        if(!query.matches(Regex("^[A-Za-z0-9]{0,4}"))){
            binding.searchInput.setError("Invalid symbol format")
            Toast.makeText(
                context, "Invalid investment ticker",
                Toast.LENGTH_SHORT).show()
            validSearch = false
        }


//        if(!binding.emailInput.text.toString().matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))){
//            binding.emailInput.setError("Invalid email format")
//            Toast.makeText(baseContext, "Invalid email format.",
//                Toast.LENGTH_SHORT).show()
//            validInputs = false
//        }

        return validSearch
    }

    fun startAddHolding(){


    }


}