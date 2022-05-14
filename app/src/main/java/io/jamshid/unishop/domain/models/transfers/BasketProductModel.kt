package io.jamshid.unishop.domain.models.transfers

import io.jamshid.unishop.domain.models.Product

// Created by Usmon Abdurakhmanv on 5/14/2022.

data class BasketProductModel(
    val id: Int,
    val product: Product,
    val quantity: Int,
    val cost: Double,
)
