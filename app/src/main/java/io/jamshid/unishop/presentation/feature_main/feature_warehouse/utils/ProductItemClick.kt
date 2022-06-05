package io.jamshid.unishop.presentation.feature_main.feature_warehouse.utils

import io.jamshid.unishop.domain.models.Product

// Created by Jamshid Isoqov an 5/29/2022
interface ProductItemClick {

    fun onClick(product: Product)
}