package io.jamshid.unishop.presentation.feature_main.feature_products.fragment_add_product.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import io.jamshid.unishop.databinding.SpinnerItemCategoryBinding
import io.jamshid.unishop.domain.models.Category

// Created by Usmon Abdurakhmanv on 5/13/2022.

class CategorySpinnerAdapter : BaseAdapter() {

    private var items: List<Category> = emptyList()

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Category = items[position]

    override fun getItemId(position: Int): Long = View.generateViewId().toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = convertView?.let {
            SpinnerItemCategoryBinding.bind(convertView)
        } ?: SpinnerItemCategoryBinding.inflate(LayoutInflater.from(parent?.context))
        binding.tvCategoryName.text = items[position].name
        return binding.root
    }

    fun submitData(items: List<Category>) {
        this.items = items
        notifyDataSetChanged()
    }
}