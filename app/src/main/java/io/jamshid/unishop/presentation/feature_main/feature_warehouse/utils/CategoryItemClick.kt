package io.jamshid.unishop.presentation.feature_main.feature_warehouse.utils

import io.jamshid.unishop.domain.models.Category

// Created by Jamshid Isoqov an 5/21/2022
interface CategoryItemClick {
    fun onClick(category: Category)
}