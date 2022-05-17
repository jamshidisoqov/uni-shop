package io.jamshid.unishop.domain.models

import io.jamshid.unishop.data.models.dto.ClientDto
import java.util.*

data class Client(
    val id: Long,
    val fullName: String,
    val phoneNumber: String,
    val personalType: Byte,
    val comment: String,
    val createdDate: Date? = null,
    val updatedDate: Date? = null,
    val createdBy: UUID? = null
) {
    fun toClientDto() = ClientDto(
        fullName = fullName,
        phoneNumber = phoneNumber,
        comment = comment,
    )
}