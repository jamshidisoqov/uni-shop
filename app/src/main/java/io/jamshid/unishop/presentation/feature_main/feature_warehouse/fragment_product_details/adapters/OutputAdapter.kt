package io.jamshid.unishop.presentation.feature_main.feature_warehouse.fragment_product_details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.R
import io.jamshid.unishop.databinding.ListItemProductOutputBinding

// Created by Jamshid Isoqov an 6/11/2022
class OutputAdapter : RecyclerView.Adapter<OutputAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemProductOutputBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemProductOutputBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_product_output, parent, false)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 100

}