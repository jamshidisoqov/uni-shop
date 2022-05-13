package io.jamshid.unishop.domain.models

import io.jamshid.unishop.data.models.dto.ClientDto

data class Client(
    val fullName: String,
    val phoneNumber: String,
    val personalType: Byte,
    val inn: Long,
    val comment: String,
) {
    fun toClientDto() = ClientDto(
        fullName = fullName,
        phoneNumber = phoneNumber,
        personalType = personalType,
        inn = inn,
        comment = comment,
    )
}