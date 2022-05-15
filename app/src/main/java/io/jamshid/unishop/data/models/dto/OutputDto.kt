package io.jamshid.unishop.data.models.dto

import java.util.*
//jibaratin
data class OutputDto(
    var clientId: Long,
    var costCash: Double,
    var costCard: Double,
    var costDebt: Double,
    var expiredDate: Date,
    var comment: String,
    var products: List<OutProductDto>
)
