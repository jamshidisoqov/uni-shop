package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.databinding.ListItemBasketBinding
import io.jamshid.unishop.domain.models.transfers.BasketProductModel

// Created by Usmon Abdurakhmanv on 5/14/2022.

class BasketListAdapter : RecyclerView.Adapter<BasketListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListItemBasketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(basketProductModel: BasketProductModel, position: Int) {
            binding.apply {

                tvProductName.text = basketProductModel.product.name
                tvProductBrand.text = basketProductModel.product.brand
                tvProductCountBasket.text = basketProductModel.quantity.toString()
                tvProductPrices.text = "Umumiy:${basketProductModel.cost * basketProductModel.quantity}"

                imgAddProduct.setOnClickListener {
                    onItemAddClick?.invoke(basketProductModel)
                    notifyItemChanged(position)
                }

                removeImg.setOnClickListener {
                    onItemRemoveClick?.invoke(basketProductModel)
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.onBind(differ.currentList[position], position)


    override fun getItemCount(): Int = differ.currentList.size

    private val differ =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<BasketProductModel>() {
            override fun areItemsTheSame(
                oldItem: BasketProductModel,
                newItem: BasketProductModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BasketProductModel,
                newItem: BasketProductModel
            ): Boolean {
                return oldItem == newItem
            }
        })

    fun submitList(baskets: List<BasketProductModel>) {
        differ.submitList(baskets)
    }

    //Listeners

    private var onItemAddClick: ((BasketProductModel) -> Unit)? = null

    fun setOnAddClickListener(onClick: (BasketProductModel) -> Unit) {
        onItemAddClick = onClick
    }

    private var onItemRemoveClick: ((BasketProductModel) -> Unit)? = null

    fun setOnRemoveClickListener(onClick: (BasketProductModel) -> Unit) {
        onItemRemoveClick = onClick
    }
}