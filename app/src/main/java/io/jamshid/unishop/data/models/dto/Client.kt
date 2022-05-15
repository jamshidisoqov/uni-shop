package io.jamshid.unishop.data.models.dto

import java.util.*

// Created by Jamshid Isoqov an 5/15/2022
data class Client(
    val id:UUID?=null,
    val fullName: String? = null,
    val phoneNumber: String? = null,
    val comment: String? = null,
    val createdDate: Date,
    val updatedDate: Date,
    val createdBy: UUID
)
