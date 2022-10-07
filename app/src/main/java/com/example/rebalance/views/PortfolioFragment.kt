package com.example.rebalance.views

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rebalance.R
import com.example.rebalance.adapters.HoldingAdapter
import com.example.rebalance.adapters.MarketAdapter
import com.example.rebalance.api.ApiClient
import com.example.rebalance.api.QuoteResponse
import com.example.rebalance.viewmodels.UserViewModel
import com.example.rebalance.databinding.FragmentPortfolioBinding
import com.example.rebalance.models.Holding
import com.example.rebalance.viewmodels.HoldingViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode


class PortfolioFragment : Fragment() {

    private var _binding:FragmentPortfolioBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private var username: String? = null
    private val holdingViewModel: HoldingViewModel by activityViewModels()
    private var userId: String? = null

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            userId = it.getString("username")
//            username = it.getString("userId")
//            println("on create")
//            println(username)
//            println(userId)


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        userViewModel.username.observe(viewLifecycleOwner, { username ->
            binding.portfolioName.text = username + "'s Portfolio"
        })

        holdingViewModel.portfolio.observe(viewLifecycleOwner, { portfolio ->
            var updatedPortfolio = ArrayList<Holding>()
            var total = BigDecimal(0)
            for(investment in portfolio){
                var investment = updateHoldingValues(investment)
                investment.calculateCostbase()
                total+= BigDecimal(investment.units) * BigDecimal(investment.curPrice)
                updatedPortfolio+=investment
            }
            var anotherPortfolio = ArrayList<Holding>()
            object:CountDownTimer(2000, 1000) {
                override fun onTick(p0: Long) {

                }

                override fun onFinish() {
                    for(investment in updatedPortfolio){
                        investment.updateWeight(total)
                        anotherPortfolio+=investment
                        println(investment.currentWeight)
                        val adapter = HoldingAdapter(anotherPortfolio)
                        val recyclerView = _binding!!.portfolioRecycler
                        var context = activity
                        recyclerView?.layoutManager = LinearLayoutManager(context)
                        recyclerView.adapter = adapter
                    }
                }
            }.start()







            binding.portfolioValue.text = "$" + total.toString()

//           return total
//    }
        })


//            binding.portfolioValue.text = holdingViewModel.calculatePortfolioValues().toString()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.addHoldingBtn.setOnClickListener{
            navController!!.navigate(R.id.action_portfolioFragment_to_searchFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateHoldingValues(holding: Holding):Holding{
        val client = ApiClient.apiService.fetchQuotes("AU", holding.code)
        client.enqueue(object : Callback<QuoteResponse> {

            override fun onResponse(
                call: Call<QuoteResponse>,
                response: Response<QuoteResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("response", response.body().toString())

                    var quotes = response.body()?.quoteResult
                    quotes?.let {

                        if(quotes.result.size > 0){

                            var investment = quotes.result[0]

                            holding.curPrice =  investment.price
                            holding.value = holding.calculateCurrentValue().toString()


                        }
                    }
                }

            }

            override fun onFailure(call: Call<QuoteResponse>, t: Throwable) {
                Log.e("Failure:", ""+t.message)
            }


        })
        return holding
    }
}


