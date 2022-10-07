package com.example.rebalance.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rebalance.R
import com.example.rebalance.databinding.ActivityMainBinding
import com.example.rebalance.models.WatchItem
import com.example.rebalance.viewmodels.HoldingViewModel
import com.example.rebalance.viewmodels.HoldingViewModelFactory
import com.example.rebalance.viewmodels.WatchItemViewModel
import com.example.rebalance.viewmodels.WatchItemViewModelFactory

class MainActivity : AppCompatActivity() {

    private var args = Bundle(1)
    private lateinit var binding: ActivityMainBinding

    private val watchItemViewModel: WatchItemViewModel by viewModels {
        WatchItemViewModelFactory((application as RebalanceApplication).watchlistRepo)
    }

    private val holdingViewModel: HoldingViewModel by viewModels {
        HoldingViewModelFactory((application as RebalanceApplication).portfolioRepo)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myIntent = intent
        val userId = myIntent.getStringExtra("user_id")
        val username = myIntent.getStringExtra("user_name")
//        args.putString("id",id)
        args.putString("userId", username)
        args.putString("username", userId)

//        newHoldingFragment.arguments = args
//        println("watchlist")
//        var watchItem2 = WatchItem("VAS.AX", "userID","Vanguard Int", "89.55", "83.00")
//        watchItemViewModel.insert(watchItem2)
        println(holdingViewModel.portfolio.value.toString())
        println(watchItemViewModel.watchlist.value.toString())


        var navigationBar = binding.mainNavigationBar
        val navController = findNavController(R.id.mainFragment)
        navController.setGraph(R.navigation.nav_graph, args)
        navigationBar.setupWithNavController(navController)


    }
    }


