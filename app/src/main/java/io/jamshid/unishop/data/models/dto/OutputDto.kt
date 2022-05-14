package io.jamshid.unishop.data.models.dto

import java.util.*

data class OutputDto(
    var clientId: UUID,
    var costCash: Double,
    var costCard: Double,
    var costDebt: Double,
    var expiredDate: Date,
    var comment: String,
    var products: List<OutProductDto>
)
