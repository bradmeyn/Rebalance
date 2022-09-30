package com.example.rebalance

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rebalance.api.ApiClient
import com.example.rebalance.api.MarketResponse
import com.example.rebalance.api.Quote
import com.example.rebalance.api.QuoteResponse
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

    lateinit var dateText:TextView
    // TODO: Rename and change types of parameters
    private var username: String? = null
    private var userId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString("username")
            username = it.getString("userId")
            println("on create")
            println(username)
            println(userId)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_markets, container, false)

        if(arguments != null){
            username = requireArguments().getString("username").toString()

            println("on create view")
            println(username)
        } else {
            println("no arguments")
        }
        var markets = "^AXJO, ^GSPC, ^FTSE, BTC-AUD"
        var titles = arrayOf("Australia", "United States", "United Kingdom", "Bitcoin")
        var imgs = arrayOf("flag_aus", "flag_usa", "flag_uk", "bitcoin")
//        ^HSI, AUDUSD=X, ^N225, BTC-AUD, ETH-AUD
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
                        println(quotes)
                        var count = 0
                        for (quote in quotes.result){
                            quote.title = titles[count]
                            quote.img = imgs[count]
                            count++
                        }
                        val adapter = MarketAdapter(quotes.result)
                        val recyclerView = view.findViewById<RecyclerView>(R.id.marketsRecyclerView)
                        var context = activity
                        recyclerView?.layoutManager = LinearLayoutManager(context)
                        recyclerView.adapter = adapter
//                        view.findViewById<TextView>(R.id.marketNameAus).text

                    }

                }
            }
            override fun onFailure(call: Call<QuoteResponse>, t:Throwable){
                Log.e("Failure:", ""+t.message)
            }
        })



        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val formattedDate = current.format(formatter)
        println(formattedDate)
        dateText = view.findViewById(R.id.dateText)
        dateText.text = formattedDate
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MarketsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MarketsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}