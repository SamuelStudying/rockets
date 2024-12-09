package com.example.icb0007_uf1_pr01_samuelmateostovar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.icb0007_uf1_pr01_samuelmateostovar.R
import com.example.icb0007_uf1_pr01_samuelmateostovar.Rocket

class RocketAdapter(private val rocketList: List<Rocket>) : RecyclerView.Adapter<RocketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RocketViewHolder(layoutInflater.inflate(R.layout.item_rocket, parent, false))
    }

    override fun getItemCount(): Int {
        return rocketList.size
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        val item = rocketList[position]
        holder.render(item)
    }
}