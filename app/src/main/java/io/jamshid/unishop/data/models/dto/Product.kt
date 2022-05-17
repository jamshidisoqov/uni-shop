package io.jamshid.unishop.data.models.dto

import io.jamshid.unishop.domain.models.Category
import java.sql.Timestamp
import java.util.*

data class Product(
    var id: Long,
    var name: String,
    var description: String,
    var brand: String,
    var quantity: Int,
    var price: Double,
    var minimumPrice: Double,
    var maximumPrice: Double,
    var category: Category,
    var createdDate: Timestamp,
    var updatedDate: Timestamp,
    var createdBy: UUID
)