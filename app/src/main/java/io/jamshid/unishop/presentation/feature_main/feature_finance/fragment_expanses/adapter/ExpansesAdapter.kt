package io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_expanses.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.R
import io.jamshid.unishop.data.models.dto.ExpansesDto
import io.jamshid.unishop.databinding.ListItemExpansesBinding

// Created by Jamshid Isoqov an 6/4/2022
class ExpansesAdapter : RecyclerView.Adapter<ExpansesAdapter.ViewHolder>() {

    private var expansesList: List<ExpansesDto> = emptyList()

    inner class ViewHolder(val binding: ListItemExpansesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(expansesDto: ExpansesDto) {
            binding.apply {
                root.setOnClickListener {
                    onItemClick?.invoke(expansesDto)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemExpansesBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_expanses, parent, false)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(expansesList[position])
    }

    override fun getItemCount(): Int = expansesList.size

    private var onItemClick: ((ExpansesDto) -> Unit)? = null

    fun setOnItemClickListener(onClick: (ExpansesDto) -> Unit) {
        onItemClick = onClick
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ExpansesDto>) {
        expansesList = list
        notifyDataSetChanged()
    }
}