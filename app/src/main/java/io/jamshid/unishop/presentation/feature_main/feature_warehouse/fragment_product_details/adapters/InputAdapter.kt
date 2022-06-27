package io.jamshid.unishop.presentation.feature_main.feature_warehouse.fragment_product_details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.R
import io.jamshid.unishop.databinding.ListItemProductIncomeBinding

// Created by Jamshid Isoqov an 6/11/2022
class InputAdapter : RecyclerView.Adapter<InputAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemProductIncomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemProductIncomeBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_product_income, parent, false)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 10
}