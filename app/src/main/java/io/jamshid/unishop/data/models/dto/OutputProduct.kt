package io.jamshid.unishop.data.models.dto

// Created by Usmon Abdurakhmanv on 5/14/2022.

data class OutputProduct(
    val id: Long? = null,
    val product: Product? = null,
    val quantity: Int? = null,
    val cost: Double? = null,
    val profitCost: Double? = null
)
