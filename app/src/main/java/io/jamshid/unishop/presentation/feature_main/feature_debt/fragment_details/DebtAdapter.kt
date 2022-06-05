package io.jamshid.unishop.presentation.feature_main.feature_debt.fragment_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.R
import io.jamshid.unishop.common.extension_functions.toSummFormat
import io.jamshid.unishop.data.models.dto.OutputProduct
import io.jamshid.unishop.databinding.ListItemProductBinding
import io.jamshid.unishop.domain.models.Product

// Created by Jamshid Isoqov an 5/21/2022
class DebtAdapter : RecyclerView.Adapter<DebtAdapter.ViewHolder>() {

    private lateinit var binding: ListItemProductBinding
    private var productList: List<OutputProduct> = emptyList()

    inner class ViewHolder(val binding: ListItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(product: OutputProduct) {
            binding.apply {
                tvItemProductName.text = product.product!!.name
                tvItemProductBrand.text = product.product.brand
                tvItemProductCount.text = product.quantity.toString()+" штук"
                tvItemProductPrice.text = product.cost!!.toLong().toString().toSummFormat()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ListItemProductBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_product, parent, false)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    fun setData(list: List<OutputProduct>) {
        productList = list
        notifyDataSetChanged()
    }



}