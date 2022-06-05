package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_payment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.R
import io.jamshid.unishop.common.extension_functions.getDateFormat
import io.jamshid.unishop.common.extension_functions.toSummFormat
import io.jamshid.unishop.data.models.dto.PaymentHistory
import io.jamshid.unishop.databinding.ListItemClientPaymentBinding
import java.util.*

// Created by Jamshid Isoqov an 5/21/2022
class PaymentAdapter : RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {

    private var paymentList: List<PaymentHistory> = emptyList()
    private lateinit var binding: ListItemClientPaymentBinding

    inner class ViewHolder(val binding: ListItemClientPaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(paymentHistory: PaymentHistory) {
            binding.apply {
                tvSummPaymentCash.text =
                    "${paymentHistory.type}:" + "${paymentHistory.amount.toLong()}".toSummFormat()
                tvPaymentDate.text = "${Date(paymentHistory.createdDate.time)}".getDateFormat()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ListItemClientPaymentBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_client_payment, parent, false)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(paymentList[position])
    }

    override fun getItemCount(): Int = paymentList.size

    fun setData(list: List<PaymentHistory>) {
        this.paymentList = list
        notifyDataSetChanged()
    }
}