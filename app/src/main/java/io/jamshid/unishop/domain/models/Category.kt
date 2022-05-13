package io.jamshid.unishop.domain.models

import io.jamshid.unishop.data.models.dto.CategoryDto

// Created by Usmon Abdurakhmanv on 5/13/2022.

data class Category(
    val id: Int,
    val name: String,
) {
    fun toCategoryDto() = CategoryDto(
        id = id,
        name = name,
    )
}
