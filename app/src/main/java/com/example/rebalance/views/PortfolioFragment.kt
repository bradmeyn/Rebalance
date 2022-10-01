package com.example.rebalance.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.rebalance.viewmodels.UserViewModel
import com.example.rebalance.databinding.FragmentPortfolioBinding


class PortfolioFragment : Fragment() {

    private var _binding:FragmentPortfolioBinding? = null
    private val binding get() = _binding!!

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
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_portfolio, container, false)
//        userViewModel.id.observe(viewLifecycleOwner, {
//            id -> binding.
//        })
        userViewModel.username.observe(viewLifecycleOwner, { username ->
            binding.portfolioName.text = username + "'s Portfolio"
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


