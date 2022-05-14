package io.jamshid.unishop.data.remote.apis

import io.jamshid.unishop.data.models.dto.ClientDto
import io.jamshid.unishop.domain.models.Client
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Created by Usmon Abdurakhmanv on 5/13/2022.

interface ClientApi {

    @GET("/client/all")
    suspend fun getAllClients(): List<Client>

    @POST("client/create")
    suspend fun addClient(@Body clientDto: ClientDto): Result<Client>
}