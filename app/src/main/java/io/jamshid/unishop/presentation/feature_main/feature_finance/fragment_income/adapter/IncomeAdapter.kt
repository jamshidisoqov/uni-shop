package io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_income.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.R
import io.jamshid.unishop.common.extension_functions.getDateFormat
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.databinding.ListItemIncomeBinding
import io.jamshid.unishop.utils.OnItemClickListener
import java.util.*

// Created by Jamshid Isoqov an 5/18/2022
class IncomeAdapter(private val onItemClickListener: OnItemClickListener<OutputSales>) :
    RecyclerView.Adapter<IncomeAdapter.ViewHolder>() {

    private var sellList: List<OutputSales> = emptyList()

    private lateinit var binding: ListItemIncomeBinding

    inner class ViewHolder(var binding: ListItemIncomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(outputSales: OutputSales) {
            binding.apply {
                tvNameBalance.text = outputSales.client!!.fullName
                //saller qoshiw karak
                tvBuyPrices.text = "${outputSales.amount}"
                tvBuyDate.text = "${Date(outputSales.updatedDate.time)}".getDateFormat()
                root.setOnClickListener {
                    onItemClickListener.onClick(outputSales)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        binding = ListItemIncomeBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_income, parent, false)
        )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(sellList[position])

    }

    override fun getItemCount(): Int = sellList.size


    fun setData(list: List<OutputSales>) {
        this.sellList = list
        notifyDataSetChanged()
    }
}