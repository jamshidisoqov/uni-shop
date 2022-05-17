package io.jamshid.unishop.data.remote.apis

import io.jamshid.unishop.data.models.dto.OutputDto
import io.jamshid.unishop.data.models.dto.OutputSales
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Created by Usmon Abdurakhmanv on 5/13/2022.

interface SaleApi {

    @GET("")
    fun getAllSales()

    @POST("output/create")
    suspend fun addOutput(@Body outputDto: OutputDto):Result<Any>

}