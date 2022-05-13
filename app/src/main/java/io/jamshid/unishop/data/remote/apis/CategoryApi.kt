package io.jamshid.unishop.data.remote.apis

import io.jamshid.unishop.data.models.dto.CategoryDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Created by Usmon Abdurakhmanv on 5/13/2022.

interface CategoryApi {

    @POST("category/create")
    suspend fun addCategory(@Body category: CategoryDto): Result<CategoryDto>

    @GET("category/all")
    suspend fun getAllCategory(): List<CategoryDto>

}