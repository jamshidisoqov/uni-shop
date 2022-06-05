package io.jamshid.unishop.data.remote.apis

import io.jamshid.unishop.data.models.dto.Client
import io.jamshid.unishop.data.models.dto.ClientDto
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.data.models.dto.PaymentHistory
import retrofit2.http.*

// Created by Usmon Abdurakhmanv on 5/13/2022.

interface ClientApi {

    @GET("client/all")
    suspend fun getAllClients(): List<Client>

    @POST("client/create")
    suspend fun addClient(@Body clientDto: ClientDto): Result<Client>

    @GET("client/{id}/history")
    suspend fun getHistoryByClient(@Path("id") id: Long): List<PaymentHistory>

    @GET("client/{id}/sales")
    suspend fun getSalesByClient(@Path("id") id: Long): List<OutputSales>

    @GET("client/get")
    suspend fun searchClient(@Query("name") name: String): List<Client>
}