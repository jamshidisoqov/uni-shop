package io.jamshid.unishop.data.remote.apis

import retrofit2.http.GET

// Created by Usmon Abdurakhmanv on 5/13/2022.

interface SaleApi {

    @GET("")
    fun getAllSales()

}