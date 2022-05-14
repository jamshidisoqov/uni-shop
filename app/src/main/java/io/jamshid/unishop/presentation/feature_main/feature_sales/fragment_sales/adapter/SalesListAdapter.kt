package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.databinding.ListItemProductBinding
import io.jamshid.unishop.domain.models.Product

// Created by Usmon Abdurakhmanv on 5/13/2022.

class SalesListAdapter : RecyclerView.Adapter<SalesListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBindView(product: Product) {
            binding.apply {
                tvItemProductName.text = product.name
                tvItemProductBrand.text = product.brand
                tvItemProductCount.text = product.quantity.toString()
                tvItemProductPrice.text = product.price.toString()

                root.setOnClickListener {
                    onItemClick?.invoke(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemProductBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.onBindView(product)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(products: List<Product>) {
        differ.submitList(products)
    }

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.brand == newItem.brand &&
                    oldItem.quantity == newItem.quantity &&
                    oldItem.price == newItem.price
        }
    })

    // Click Listeners

    private var onItemClick: ((Product) -> Unit)? = null

    fun setOnItemClickListener(onClick: (Product) -> Unit) {
        onItemClick = onClick
    }
}