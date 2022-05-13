package io.jamshid.unishop.data.remote.apis

import io.jamshid.unishop.common.Result
import io.jamshid.unishop.data.models.dto.ProductDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Created by Usmon Abdurakhmanv on 5/13/2022.

interface ProductApi {

    @GET("product/all")
    suspend fun getAllProduct(): List<ProductDto>

    @POST("input/create")
    suspend fun addProduct(@Body productDto: ProductDto): Result<ProductDto>

}