package com.example.icb0007_uf1_pr01_samuelmateostovar.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.icb0007_uf1_pr01_samuelmateostovar.R
import com.example.icb0007_uf1_pr01_samuelmateostovar.Rocket

class RocketViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val rocket = view.findViewById<TextView>(R.id.tvRocketName)

    fun render(rocketModel: Rocket) {
        rocket.text = rocketModel.name
    }
}