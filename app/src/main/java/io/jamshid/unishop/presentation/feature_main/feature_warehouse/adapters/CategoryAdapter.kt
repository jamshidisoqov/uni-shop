package io.jamshid.unishop.presentation.feature_main.feature_warehouse.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.R
import io.jamshid.unishop.databinding.ListItemCategoryBinding
import io.jamshid.unishop.domain.models.Category
import io.jamshid.unishop.presentation.feature_main.feature_warehouse.utils.CategoryItemClick

// Created by Jamshid Isoqov an 5/21/2022
class CategoryAdapter(private val onItemClick: CategoryItemClick, private val drawable: Drawable,private val drawable2:Drawable) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var binding: ListItemCategoryBinding
    private var categoryList: List<Category> = emptyList()


    inner class ViewHolder(val binding: ListItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int, category: Category) {
            binding.apply {
                tvCategoryName.text = category.name
                root.setOnClickListener {
                    if (onItemClick.onClick(category)) {
                        root.background = drawable
                    } else {
                        root.background = drawable2
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ListItemCategoryBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_category, parent, false)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position,categoryList[position])
    }

    override fun getItemCount(): Int = categoryList.size

    fun setData(list: List<Category>) {
        categoryList = list
        notifyDataSetChanged()
    }
}