package com.example.icb0007_uf1_pr01_samuelmateostovar.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.icb0007_uf1_pr01_samuelmateostovar.viewmodel.MainViewModel
import com.example.icb0007_uf1_pr01_samuelmateostovar.viewmodel.MainViewModelFactory
import com.example.icb0007_uf1_pr01_samuelmateostovar.R
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.RocketRepository
import com.example.icb0007_uf1_pr01_samuelmateostovar.ui.adapters.RocketAdapter
import kotlinx.coroutines.launch

class RocketListFragment : Fragment() {

    private val repository by lazy { RocketRepository(requireContext()) }
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(repository) }

    private lateinit var rocketAdapter: RocketAdapter
    private lateinit var rvRockets: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.rocket_list_fragment, container, false)
        initRecyclerView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        viewModel.fetchRockets()
    }

    private fun initRecyclerView(view : View) {
        rvRockets = view.findViewById(R.id.rvRockets)
        rocketAdapter = RocketAdapter()
        rvRockets.adapter = rocketAdapter
        rvRockets.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.rocketUiList.collect { rocketList ->
                rocketAdapter.submitList(rocketList)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorState.collect { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}