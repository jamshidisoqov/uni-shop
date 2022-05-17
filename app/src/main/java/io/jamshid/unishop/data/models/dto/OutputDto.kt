package io.jamshid.unishop.data.models.dto

//jibaratin
data class OutputDto(
    var clientId: Long,
    var costCash: Double,
    var costCard: Double,
    var costDebt: Double,
    var expiredDate: Long? = null,
    var comment: String,
    var products: List<OutProductDto>
)
