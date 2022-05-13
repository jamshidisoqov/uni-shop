package io.jamshid.unishop.data.models.dto

import io.jamshid.unishop.domain.models.Category

data class CategoryDto(
    val id: Int? = null,
    val name: String? = null,
) {
    fun toCategory() = Category(
        id = id ?: 0,
        name = name ?: "",
    )
}