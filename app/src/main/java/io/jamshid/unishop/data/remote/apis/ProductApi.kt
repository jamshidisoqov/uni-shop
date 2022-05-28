package io.jamshid.unishop.data.remote.apis

import io.jamshid.unishop.common.Result
import io.jamshid.unishop.data.models.dto.ProductDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Created by Usmon Abdurakhmanv on 5/13/2022.

interface ProductApi {

    @GET("product/all")
    suspend fun getAllProduct(): List<ProductDto>

    @POST("input/create")
    suspend fun addProduct(@Body productDto: ProductDto): Result<ProductDto>

    @GET("product/{id}")
    suspend fun getProduct(@Path("id") id: Long): ProductDto

}