package io.jamshid.unishop.data.remote.apis

import io.jamshid.unishop.common.Result
import io.jamshid.unishop.data.models.dto.ProductDto
import retrofit2.http.*

// Created by Isoqov Jamshid on 5/13/2022.

interface ProductApi {

    @GET("product/all")
    suspend fun getAllProduct(): List<ProductDto>

    @POST("input/create")
    suspend fun addProduct(@Body productDto: ProductDto): Result<ProductDto>

    @GET("product/{id}")
    suspend fun getProduct(@Path("id") id: Long): ProductDto

    @PUT("input/create")
    suspend fun putProduct(@Body productDto: ProductDto): Result<ProductDto>

    @PUT("product/{id}")
    suspend fun updateProduct(
        @Path("id") id: Long,
        @Body productDto: ProductDto
    ): Result<ProductDto>

    @GET("product/get")
    suspend fun searchProduct(@Query("name") name: String): List<ProductDto>

}