package io.jamshid.unishop.domain.models

import io.jamshid.unishop.data.models.dto.ProductDto

// Created by Usmon Abdurakhmanv on 5/13/2022.

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val brand: String,
    val quantity: Int,
    val price: Double,
    val minimumPrice: Double,
    val maximumPrice: Double,
    val categoryId: Int,
) {
    fun toProductDto() = ProductDto(
        id = id,
        name = name,
        description = description,
        brand = brand,
        quantity = quantity,
        price = price,
        minimumPrice = minimumPrice,
        maximumPrice = maximumPrice,
        categoryId = categoryId,
    )
}