package com.example.rebalance.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rebalance.R
import com.example.rebalance.adapters.HoldingAdapter
import com.example.rebalance.adapters.WatchlistAdapter
import com.example.rebalance.database.AppDatabase
import com.example.rebalance.databinding.FragmentPortfolioBinding
import com.example.rebalance.databinding.FragmentWatchlistBinding
import com.example.rebalance.repository.WatchItemRepository
import com.example.rebalance.viewmodels.UserViewModel
import com.example.rebalance.viewmodels.WatchItemViewModel
import com.example.rebalance.viewmodels.WatchItemViewModelFactory


class WatchlistFragment : Fragment() {

    private var _binding:FragmentWatchlistBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val userViewModel: UserViewModel by activityViewModels()
    private val watchItemViewModel: WatchItemViewModel by activityViewModels()
    private var userId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userViewModel.id.observe(viewLifecycleOwner, { id ->
            userId = id
            println("USERID")
            println(userId)

        })

        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)


          watchItemViewModel.watchlist.observe(viewLifecycleOwner, { otherlist ->
              val adapter = WatchlistAdapter(otherlist)
              val recyclerView = _binding!!.watchlistRecycler
              var context = activity
              recyclerView?.layoutManager = LinearLayoutManager(context)
              recyclerView.adapter = adapter
          })




//        watchItemViewModel.watchlist

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.addInvestmentBtn.setOnClickListener{
            navController!!.navigate(R.id.action_watchlistFragment_to_searchFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}