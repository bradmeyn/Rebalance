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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rebalance.R
import com.example.rebalance.adapters.HoldingAdapter
import com.example.rebalance.adapters.MarketAdapter
import com.example.rebalance.adapters.WatchlistAdapter
import com.example.rebalance.api.ApiClient
import com.example.rebalance.api.QuoteResponse
import com.example.rebalance.viewmodels.UserViewModel
import com.example.rebalance.databinding.FragmentPortfolioBinding
import com.example.rebalance.models.Holding
import com.example.rebalance.models.Investment
import com.example.rebalance.models.WatchItem
import com.example.rebalance.viewmodels.HoldingViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS
import com.github.mikephil.charting.utils.ColorTemplate.VORDIPLOM_COLORS
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.util.*
import kotlin.collections.ArrayList


class PortfolioFragment : Fragment() {

    private var _binding:FragmentPortfolioBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private var username: String? = null
    private var userId: String? = null
    private val holdingViewModel: HoldingViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var adapter: HoldingAdapter
    private lateinit var recyclerView:RecyclerView
    private lateinit var list: List<Holding>
    lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
           if(portfolio.size > 0){
               list = portfolio
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
                           adapter = HoldingAdapter(anotherPortfolio)
                           recyclerView = _binding!!.portfolioRecycler
                           var context = activity
                           recyclerView?.layoutManager = LinearLayoutManager(context)
                           recyclerView.adapter = adapter
                           binding.portfolioValue.text = "$" + total.toString()
                           mIth.attachToRecyclerView(recyclerView)
                           pieChart = binding.portfolioPieChart
                           loadPieChart(updatedPortfolio)
                       }
                   }
               }.start()
           }

//    }
        })


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

    fun loadPieChart(portfolio: List<Holding>){
    var pieValues = ArrayList<PieEntry>()

        var count = 0
        for(investment in portfolio){
            pieValues.add(PieEntry(investment.currentWeight.toBigDecimal().toFloat(), investment.code))
        }

        var colors = mutableListOf<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS){
            colors.add(color)
        }

        var pieDataSet = PieDataSet(pieValues, "")
        pieDataSet.colors = colors
        var data = PieData(pieDataSet)
        data.setValueTextColor(Color.BLACK)
        data.setValueTextSize(13f)
        pieChart.setEntryLabelColor(Color.TRANSPARENT)
        data.setValueFormatter(PercentFormatter(pieChart))
        pieChart.data = data
        pieChart.animateY(1500,Easing.EaseInOutQuad)
        pieChart.setUsePercentValues(true)
        pieChart.legend.textSize = 14f
        pieChart.description.isEnabled = false
        pieChart.legend.horizontalAlignment
    }

    var mIth = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT.or(ItemTouchHelper.LEFT)
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var position = viewHolder.bindingAdapterPosition

                when(direction){
                    ItemTouchHelper.RIGHT -> {
                        var swipedItem = list.get(position)
                        holdingViewModel.delete(swipedItem)
                        adapter.notifyItemRemoved(position)


                        Snackbar.make(recyclerView, "${swipedItem.code} has been removed", Snackbar.LENGTH_LONG).setAction("Undo", View.OnClickListener {
                            holdingViewModel.insert(swipedItem)
//                                adapter.notifyItemInserted()
                        }).show()
                    }

                    ItemTouchHelper.LEFT -> {
                        var swipedItem = list.get(position)

                        userViewModel.setSelectedInvestment(
                            Investment(swipedItem.name,swipedItem.code,swipedItem.curPrice,swipedItem.units,swipedItem.avePrice,swipedItem.targetWeight)
                        )
                        navController!!.navigate(R.id.action_portfolioFragment_to_addHoldingFragment2)

                    }
                }
            }
        })
}


