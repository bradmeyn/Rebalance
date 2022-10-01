package com.example.rebalance.views

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rebalance.adapters.MarketAdapter
import com.example.rebalance.viewmodels.UserViewModel
import com.example.rebalance.api.ApiClient
import com.example.rebalance.api.QuoteResponse
import com.example.rebalance.databinding.FragmentMarketsBinding
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarketsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarketsFragment : Fragment() {

    private var username: String? = null
    private var userId: String? = null
    private var _binding: FragmentMarketsBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString("username")
            username = it.getString("userId")
            println("on create")
            userId?.let { it1 -> userViewModel.setId(it1) }
            userViewModel.setUsername(username!!)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarketsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
//        var view =  inflater.inflate(R.layout.fragment_markets, container, false)

        if(arguments != null){
            username = requireArguments().getString("username").toString()

        } else {
            println("no arguments")
        }
        getMarkets()

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val formattedDate = current.format(formatter)
        _binding!!.dateText.text = formattedDate

        return binding.root
    }

    fun getMarkets(){
        var markets = "^AXJO, ^GSPC, ^FTSE, AUD-BTC"
        //        ^HSI, AUDUSD=X, ^N225, BTC-AUD, ETH-AUD
        var titles = arrayOf("Australia", "United States", "United Kingdom", "Bitcoin")
        var imgs = arrayOf("flag_aus", "flag_usa", "flag_uk", "bitcoin")
        val client = ApiClient.apiService.fetchQuotes("AU", markets )
        client.enqueue(object : Callback<QuoteResponse> {

            override fun onResponse(
                call: Call<QuoteResponse>,
                response: Response<QuoteResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("response", response.body().toString())
                    var quotes = response.body()?.quoteResult
                    quotes?.let {
                        var count = 0
                        for (quote in quotes.result){
                            quote.title = titles[count]
                            quote.img = imgs[count]
                            count++
                        }
                        val adapter = MarketAdapter(quotes.result)
                        val recyclerView = _binding!!.marketsRecyclerView
                        var context = activity
                        recyclerView?.layoutManager = LinearLayoutManager(context)
                        recyclerView.adapter = adapter
                    }

                }
            }
            override fun onFailure(call: Call<QuoteResponse>, t:Throwable){
                Log.e("Failure:", ""+t.message)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    }




