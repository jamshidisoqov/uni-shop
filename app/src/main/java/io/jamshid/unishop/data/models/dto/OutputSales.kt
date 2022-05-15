package io.jamshid.unishop.data.models.dto

import java.util.*

// Created by Jamshid Isoqov an 5/15/2022
data class OutputSales(
    val id: Long,
    val client: Client,
    val products: Set<OutputProduct>,
    val paymentHistories: Set<PaymentHistory>,
    val status: PaymentStatus,
    val debtAmount: Double,
    val expiredDate: Date,
    val comment: String,
    val createdDate: Date,
    val updatedDate: Date,
    val createdBy: UUID
)