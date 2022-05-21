package io.jamshid.unishop.data.remote.apis

import io.jamshid.unishop.data.models.dto.OutputDto
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.common.Result
import io.jamshid.unishop.data.models.dto.ProductByOutput
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Created by Usmon Abdurakhmanv on 5/13/2022.

interface SaleApi {

    @GET("output/all")
    suspend fun getAllSales(): List<OutputSales>

    @POST("output/create")
    suspend fun addOutput(@Body outputDto: OutputDto): Result<OutputSales>

    @GET("output/{outputId}/products")
    suspend fun getProductsByOutPut(@Path("outputId") outputId:Long):List<ProductByOutput>

}