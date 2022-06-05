package io.jamshid.unishop.data.remote.apis

import io.jamshid.unishop.data.models.dto.WorkerDto
import retrofit2.http.GET
import retrofit2.http.Query

// Created by Jamshid Isoqov an 6/5/2022

interface WorkerApi {

    @GET("")
    suspend fun getAllWorker(): List<WorkerDto>

    @GET("output/byDate")
    suspend fun getSalesByDate(
        @Query("from") from: Long,
        @Query("to") to: Long
    ): List<WorkerDto>

}