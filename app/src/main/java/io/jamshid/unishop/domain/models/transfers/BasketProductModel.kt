package io.jamshid.unishop.domain.models.transfers

import io.jamshid.unishop.data.models.dto.OutProductDto
import io.jamshid.unishop.domain.models.Product
import java.util.*

// Created by Usmon Abdurakhmanv on 5/14/2022.

data class BasketProductModel(
    val id: Long,
    val product: Product,
    val quantity: Int,
    val cost: Double,
) {
    fun toOutputDto() = OutProductDto(
        productId = product.id,
        quantity = quantity,
        cost = cost
    )
}