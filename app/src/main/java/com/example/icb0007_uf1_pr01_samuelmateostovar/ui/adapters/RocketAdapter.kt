package com.example.icb0007_uf1_pr01_samuelmateostovar.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.icb0007_uf1_pr01_samuelmateostovar.R
import com.example.icb0007_uf1_pr01_samuelmateostovar.models.RocketUi

class RocketAdapter : ListAdapter<RocketUi, RocketAdapter.RocketViewHolder>(RocketDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rocket, parent, false)
        return RocketViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        val rocket = getItem(position)
        holder.bind(rocket)
    }

    class RocketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvRocketName)

        fun bind(rocket: RocketUi) {
            name.text = rocket.name
        }
    }

    class RocketDiffCallBack : DiffUtil.ItemCallback<RocketUi>() {
        override fun areItemsTheSame(oldItem: RocketUi, newItem: RocketUi): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: RocketUi, newItem: RocketUi): Boolean {
            return oldItem == newItem
        }
    }
}