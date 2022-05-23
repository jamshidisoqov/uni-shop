package io.jamshid.unishop.data.models.dto

import java.io.Serializable
import java.sql.Timestamp

// Created by Usmon Abdurakhmanv on 5/14/2022.

data class Output(
    val id: Long,
    val client: String,
    val status: PaymentStatus,
    val debtAmount: Double,
    val expiredDate: Timestamp,
    val comment: String,
    val amount:Double,
    val createdDate: Timestamp,
    val updatedDate: Timestamp?=null,
    ):Serializable