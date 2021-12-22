package com.study.viewpagerindicator.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.viewpagerindicator.databinding.CardHolderBinding

//
//  CardAdapter.kt
//  ViewpagerIndicator
//
//  Created by Seomoon Choi on 9/2/21
//

class CardAdapter : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    var items: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(CardHolderBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(private val binding: CardHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: String,
        ) {
            binding.data = item
        }
    }
}