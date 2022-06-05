package io.jamshid.unishop.data.models.dto

import java.sql.Timestamp

// Created by Jamshid Isoqov an 6/5/2022
data class ExpansesDto(
    val workerDto: WorkerDto,
    val date: Timestamp,
    val comment: String,
    val sum: Double,
    val categoryName: String
)