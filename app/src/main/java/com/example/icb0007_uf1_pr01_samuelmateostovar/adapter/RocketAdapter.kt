package com.example.icb0007_uf1_pr01_samuelmateostovar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.icb0007_uf1_pr01_samuelmateostovar.R
import com.example.icb0007_uf1_pr01_samuelmateostovar.RocketUi

class RocketAdapter(private val rocketUiApis: List<RocketUi>) : RecyclerView.Adapter<RocketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rocket, parent, false)
        return RocketViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        val rocket = rocketUiApis[position]
        holder.name.text = rocket.name
    }

    override fun getItemCount(): Int {
        return rocketUiApis.size
    }
}