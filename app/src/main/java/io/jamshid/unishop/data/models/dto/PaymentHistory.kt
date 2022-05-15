package io.jamshid.unishop.data.models.dto

import java.sql.Timestamp
import java.util.*

// Created by Usmon Abdurakhmanv on 5/14/2022.

class PaymentHistory(
    val id: Long,
    val amount: Double,
    val type: PaymentType,
    val createdDate: Timestamp,
    val createdBy: UUID
)