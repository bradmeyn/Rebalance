package com.example.rebalance.views

import android.os.Bundle
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
import com.example.rebalance.viewmodels.UserViewModel
import com.example.rebalance.databinding.FragmentPortfolioBinding


class PortfolioFragment : Fragment() {

    private var _binding:FragmentPortfolioBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private var username: String? = null
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

        userViewModel.portfolio.observe(viewLifecycleOwner, { portfolio ->
            val adapter = HoldingAdapter(portfolio)
            val recyclerView = _binding!!.portfolioRecycler
            var context = activity
            recyclerView?.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
//           binding.portfolioValue.text =  userViewModel.calculatePortfolioTotal()
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
}


