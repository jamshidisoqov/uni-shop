package io.jamshid.unishop.data.models.dto

import java.util.*

data class OutProductDto(
    var productId: UUID,
    var quantity: Int,
    var cost: Double
)
