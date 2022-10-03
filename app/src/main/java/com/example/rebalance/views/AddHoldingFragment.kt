package com.example.rebalance.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.rebalance.*
import com.example.rebalance.database.AppDatabase
import com.example.rebalance.databinding.FragmentAddHoldingBinding
import com.example.rebalance.models.Holding
import com.example.rebalance.models.Investment
import com.example.rebalance.viewmodels.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddHoldingFragment : Fragment(R.layout.fragment_add_holding) {

    private var _binding: FragmentAddHoldingBinding? = null
    private val binding get() = _binding!!
    private lateinit var newHolding: Holding
    private lateinit var appDatabase: AppDatabase
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var userId:String
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddHoldingBinding.inflate(inflater, container, false)
        userViewModel.id.observe(viewLifecycleOwner) { id ->
            userId = id;
        }

        userViewModel.selectedInvestment.observe(viewLifecycleOwner) { investment:Investment ->
            binding.investmentName.text = investment.name
            binding.investmentCode.text = investment.code
            binding.investmentPrice.text = investment.price
        }

        binding.addHoldingBtn.setOnClickListener {

            userViewModel.selectedInvestment.observe(viewLifecycleOwner) { selectedInvestment: Investment ->

                newHolding = Holding(
                    null,
                    userId,
                    selectedInvestment.name,
                    selectedInvestment.code,
                    binding.unitsInput.text.toString().toInt(),
                    binding.priceInput.text.toString(),
                    binding.targetInput.text.toString(),
                    selectedInvestment.price,
                    "weight",
                    "value"
                )

//                userViewModel.addHolding(newHolding)
            }
            navController!!.navigate(R.id.action_addHoldingFragment_to_portfolioFragment)
//            saveHolding(newHolding)
          }



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun saveHolding(newHolding: Holding){
//        GlobalScope.launch(Dispatchers.IO){
//            appDatabase.holdingDao().addHolding(newHolding)
//        }
//    }


}