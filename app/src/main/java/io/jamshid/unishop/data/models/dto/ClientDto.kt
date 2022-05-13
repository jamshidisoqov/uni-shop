package io.jamshid.unishop.data.models.dto

import io.jamshid.unishop.domain.models.Client

data class ClientDto(
    val fullName: String? = null,
    val phoneNumber: String? = null,
    val personalType: Byte? = null,
    val inn: Long? = null,
    val comment: String? = null,
) {
    fun toClient() = Client(
        fullName = fullName ?: "",
        phoneNumber = phoneNumber ?: "",
        personalType = personalType ?: 0,
        inn = inn ?: 0L,
        comment = comment ?: "",
    )
}