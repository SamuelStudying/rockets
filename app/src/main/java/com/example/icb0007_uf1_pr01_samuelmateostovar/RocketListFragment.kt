package com.example.icb0007_uf1_pr01_samuelmateostovar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.icb0007_uf1_pr01_samuelmateostovar.adapter.RocketAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RocketListFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private val rocketUiList = mutableListOf<RocketUi>()
    private lateinit var rvRockets: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.rocket_list_fragment, container, false)
        initRecyclerView(view)
        fetchRockets()
        return view
    }

    private fun fetchRockets() {
        viewModel.fetchRockets(requireContext())
        lifecycleScope.launch {
            viewModel.rocketUiListFlow.collect {
                rocketUiList.clear()
                rocketUiList.addAll(it)
                rvRockets.adapter?.notifyDataSetChanged()
            }
        }
    }


    private fun initRecyclerView(view : View) {
        rvRockets = view.findViewById(R.id.rvRockets)
        rvRockets.adapter = RocketAdapter(rocketUiList)
        rvRockets.layoutManager = LinearLayoutManager(requireContext())
    }
}