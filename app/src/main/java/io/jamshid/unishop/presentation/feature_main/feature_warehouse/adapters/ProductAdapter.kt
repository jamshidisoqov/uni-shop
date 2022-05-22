package io.jamshid.unishop.presentation.feature_main.feature_warehouse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.R
import io.jamshid.unishop.databinding.ListItemProductBinding
import io.jamshid.unishop.domain.models.Product

// Created by Jamshid Isoqov an 5/21/2022
class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var binding: ListItemProductBinding
    private var productList: List<Product> = emptyList()

    inner class ViewHolder(val binding: ListItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(product: Product) {
            binding.apply {
                tvItemProductName.text = product.name
                tvItemProductBrand.text = product.brand
                tvItemProductCount.text = product.quantity.toString()
                tvItemProductPrice.text = product.price.toString()
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

    fun setData(list: List<Product>) {
        productList = list
        notifyDataSetChanged()
    }


}