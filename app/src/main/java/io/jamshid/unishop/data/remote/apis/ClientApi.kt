package io.jamshid.unishop.data.remote.apis

import io.jamshid.unishop.data.models.dto.ClientDto
import retrofit2.http.GET

// Created by Usmon Abdurakhmanv on 5/13/2022.

interface ClientApi {

    @GET("/client/all")
    suspend fun getAllClients(): List<ClientDto>
}