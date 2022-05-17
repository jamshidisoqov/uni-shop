package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import io.jamshid.unishop.data.models.dto.Client
import io.jamshid.unishop.databinding.SpinnerItemOrderBinding

// Created by Usmon Abdurakhmanv on 5/14/2022.

class OrderSpinnerAdapter : BaseAdapter() {

    private var list: List<Client> = emptyList()

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Client = list[position]

    override fun getItemId(position: Int): Long = View.generateViewId().toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = convertView?.let {
            SpinnerItemOrderBinding.bind(convertView)
        } ?: SpinnerItemOrderBinding.inflate(LayoutInflater.from(parent?.context))
        binding.tnClientName.text = list[position].fullName
        return binding.root
    }


    fun submitList(list: List<Client>) {
        this.list = list
        notifyDataSetChanged()
    }
}