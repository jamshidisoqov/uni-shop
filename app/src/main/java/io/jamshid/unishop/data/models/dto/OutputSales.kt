package io.jamshid.unishop.data.models.dto

import java.sql.Timestamp
import java.util.*

// Created by Jamshid Isoqov an 5/15/2022

data class OutputSales(
    val id: Long,
    val client: Client,
    val status: PaymentStatus,
    val debtAmount: Double,
    val expiredDate: Date,
    val comment: String,
    val amount: Double,
    val createdDate: Timestamp,
    val updatedDate: Timestamp,
    val createdBy: UUID? = null
)