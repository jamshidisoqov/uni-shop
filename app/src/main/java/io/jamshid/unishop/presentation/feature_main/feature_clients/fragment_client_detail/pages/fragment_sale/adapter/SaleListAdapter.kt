package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.databinding.ListItemSeleListBinding

// Created by Usmon Abdurakhmanv on 5/14/2022.

class SaleListAdapter : RecyclerView.Adapter<SaleListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemSeleListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBindView(outputSales: OutputSales) {

            binding.apply {
                tvNameBalans.text = outputSales.client.fullName
                tvSallerNameBalans.text = outputSales.createdBy.toString()
                "${outputSales.amount} UZS".also { tvBuyPrices.text = it }
                tvBuyDate.text = outputSales.createdDate.toString()

                root.setOnClickListener {
                    onItemClick?.invoke(outputSales)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemSeleListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<OutputSales>() {
        override fun areItemsTheSame(oldItem: OutputSales, newItem: OutputSales): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OutputSales, newItem: OutputSales): Boolean {
            return oldItem == newItem
        }
    })

    fun submitList(list: List<OutputSales>) {
        differ.submitList(list)
    }

    private var onItemClick: ((OutputSales) -> Unit)? = null

    fun setOnItemClickListener(onClick: (OutputSales) -> Unit) {
        onItemClick = onClick
    }
}