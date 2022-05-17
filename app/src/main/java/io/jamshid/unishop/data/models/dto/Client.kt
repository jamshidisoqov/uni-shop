package io.jamshid.unishop.data.models.dto

import java.io.Serializable
import java.sql.Timestamp
import java.util.*

// Created by Jamshid Isoqov an 5/15/2022
data class Client(
    val id: Long? = null,
    val fullName: String? = null,
    val phoneNumber: String? = null,
    val comment: String? = null,
    val createdDate: Timestamp,
    val updatedDate: Timestamp,
    val createdBy: UUID
) : Serializable