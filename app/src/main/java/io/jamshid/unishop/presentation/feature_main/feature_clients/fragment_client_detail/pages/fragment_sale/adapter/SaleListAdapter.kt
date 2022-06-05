package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.R
import io.jamshid.unishop.common.extension_functions.getDateFormat
import io.jamshid.unishop.common.extension_functions.toSummFormat
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.databinding.ListItemSaleListBinding
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale.utils.SalesClickListener
import java.util.*

// Created by Usmon Abdurakhmanv on 5/14/2022.

class SaleListAdapter(private val salesClickListener: SalesClickListener) : RecyclerView.Adapter<SaleListAdapter.ViewHolder>() {

    private lateinit var binding: ListItemSaleListBinding
    private var salesList: List<OutputSales> = emptyList()

    inner class ViewHolder(val binding: ListItemSaleListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(outputSales: OutputSales) {
            binding.apply {
                tvNameBalans.text = outputSales.client.fullName
                tvBuyPrices.text = outputSales.amount.toLong().toString().toSummFormat()
                tvBuyDate.text = "${Date(outputSales.createdDate.time)}".getDateFormat()
                root.setOnClickListener {
                    salesClickListener.onClick(outputSales)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ListItemSaleListBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_sale_list, parent, false)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBind(salesList[position])

    }

    override fun getItemCount(): Int = salesList.size

    fun setData(list: List<OutputSales>) {
        this.salesList = list
        notifyDataSetChanged()
    }

}