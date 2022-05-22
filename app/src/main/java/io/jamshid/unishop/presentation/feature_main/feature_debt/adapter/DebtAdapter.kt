package io.jamshid.unishop.presentation.feature_main.feature_debt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.R
import io.jamshid.unishop.common.extension_functions.getDateFormat
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.databinding.ListItemDebtBinding
import io.jamshid.unishop.presentation.feature_main.feature_debt.util.DebtClickListener

// Created by Jamshid Isoqov an 5/22/2022
class DebtAdapter(private val debtClickListener: DebtClickListener) :
    RecyclerView.Adapter<DebtAdapter.ViewHolder>() {

    private lateinit var binding: ListItemDebtBinding
    private var debtList: List<OutputSales> = emptyList()

    inner class ViewHolder(val binding: ListItemDebtBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind(outputSales: OutputSales) {
            binding.apply {
                tvDebtUserName.text = outputSales.client.fullName
                tvDebtSumm.text = outputSales.debtAmount.toString()
                tvDebtDate.text = outputSales.expiredDate.toString().getDateFormat()
                root.setOnClickListener {
                    debtClickListener.onClick(outputSales)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ListItemDebtBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_debt, parent, false)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(debtList[position])
    }

    override fun getItemCount(): Int = debtList.size

    fun setData(list: List<OutputSales>) {
        debtList = list
        notifyDataSetChanged()
    }
}