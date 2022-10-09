package com.example.rebalance.views

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.rebalance.R
import com.example.rebalance.adapters.WatchlistAdapter
import com.example.rebalance.databinding.FragmentWatchlistBinding
import com.example.rebalance.models.WatchItem
import com.example.rebalance.viewmodels.UserViewModel
import com.example.rebalance.viewmodels.WatchItemViewModel
import com.google.android.material.snackbar.Snackbar


class WatchlistFragment : Fragment() {

    private var _binding:FragmentWatchlistBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val userViewModel: UserViewModel by activityViewModels()
    private val watchItemViewModel: WatchItemViewModel by activityViewModels()
    private lateinit var adapter: WatchlistAdapter
    private var userId: String? = null
    private lateinit var list: List<WatchItem>
    private lateinit var recyclerView:RecyclerView


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
        })

        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)


          watchItemViewModel.watchlist.observe(viewLifecycleOwner, { otherlist ->
              list = otherlist
              adapter = WatchlistAdapter(otherlist)
              recyclerView = _binding!!.watchlistRecycler
              var context = activity
              recyclerView?.layoutManager = LinearLayoutManager(context)
              recyclerView.adapter = adapter
              mIth.attachToRecyclerView(recyclerView)



          })



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


    var mIth = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder,
                target: ViewHolder
            ): Boolean {
            return false
            }


            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                var position = viewHolder.bindingAdapterPosition

                    when(direction){
                        ItemTouchHelper.RIGHT -> {

                            var deletedItem = list.get(position)
                            watchItemViewModel.delete(deletedItem)
                            adapter.notifyItemRemoved(position)

                            Snackbar.make(recyclerView, "${deletedItem.code} has been removed", Snackbar.LENGTH_LONG).setAction("Undo", View.OnClickListener {
                                watchItemViewModel.insert(deletedItem)
//                                adapter.notifyItemInserted()
                            }).show()

                        }

                    }


            }
        })


}