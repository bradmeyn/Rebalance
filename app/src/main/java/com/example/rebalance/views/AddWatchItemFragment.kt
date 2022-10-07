package com.example.rebalance.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.rebalance.Helper
import com.example.rebalance.R
import com.example.rebalance.database.AppDatabase
import com.example.rebalance.databinding.FragmentAddHoldingBinding
import com.example.rebalance.databinding.FragmentAddWatchItemBinding
import com.example.rebalance.models.Holding
import com.example.rebalance.models.Investment
import com.example.rebalance.models.WatchItem
import com.example.rebalance.viewmodels.UserViewModel
import com.example.rebalance.viewmodels.WatchItemViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddWatchItemFragment : Fragment() {

    private var _binding: FragmentAddWatchItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var newWatchlistItem: WatchItem
    private val userViewModel: UserViewModel by activityViewModels()
    private val watchItemViewModel: WatchItemViewModel by activityViewModels()
    private lateinit var userId:String
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
        _binding = FragmentAddWatchItemBinding.inflate(inflater, container, false)
        userViewModel.id.observe(viewLifecycleOwner) { id ->
            userId = id;
        }


        userViewModel.selectedInvestment.observe(viewLifecycleOwner) { investment: Investment ->
            binding.investmentName.text = investment.name
            binding.investmentCode.text = investment.code
            binding.investmentPrice.text = investment.price
        }

        binding.addItemBtn.setOnClickListener {

            if(Helper.isValidPrice(binding.targetPriceInput)){
                userViewModel.selectedInvestment.observe(viewLifecycleOwner) { selectedInvestment: Investment ->
                    newWatchlistItem = WatchItem(
                        selectedInvestment.code,
                        userId,
                        selectedInvestment.name,
                        selectedInvestment.price,
                        binding.targetPriceInput.text.toString()
                    )
                }
                watchItemViewModel.insert(newWatchlistItem)
                navController!!.navigate(R.id.action_addWatchItemFragment_to_watchlistFragment)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

    }
}