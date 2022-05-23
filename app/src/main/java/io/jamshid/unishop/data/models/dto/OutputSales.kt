package io.jamshid.unishop.data.models.dto

import java.io.Serializable
import java.sql.Timestamp
import java.util.*

// Created by Jamshid Isoqov an 5/15/2022
data class OutputSales(
    val id: Long,
    val client: Client,
    val status: PaymentStatus,
    val debtAmount: Double,
    val expiredDate: Timestamp,
    val comment: String,
    val amount: Double,
    val createdDate: Timestamp,
    val updatedDate: Timestamp,
    val createdBy: UUID
) : Serializable {
    fun toOutput(): Output {
        return Output(
            id = id,
            client = client.fullName!!,
            status = status,
            debtAmount,
            expiredDate,
            comment,
            amount,
            createdDate,
            updatedDate
        )
    }
}