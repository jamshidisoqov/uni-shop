package io.jamshid.unishop.data.models.dto

import io.jamshid.unishop.domain.models.Product
import java.util.*

// Created by Usmon Abdurakhmanv on 5/13/2022.

data class ProductDto(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val brand: String? = null,
    val quantity: Int? = null,
    val price: Double? = null,
    val minimumPrice: Double? = null,
    val maximumPrice: Double? = null,
    val categoryId: Int? = null,
) {
    fun toProduct() = Product(
        id = id!!,
        name = name ?: "",
        description = description ?: "",
        brand = brand ?: "",
        quantity = quantity ?: 0,
        price = price ?: 0.0,
        minimumPrice = minimumPrice ?: 0.0,
        maximumPrice = maximumPrice ?: 0.0,
        categoryId = categoryId ?: 0,
    )
}