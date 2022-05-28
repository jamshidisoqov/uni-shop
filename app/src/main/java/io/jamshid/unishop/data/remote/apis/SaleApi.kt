package io.jamshid.unishop.data.remote.apis

import io.jamshid.unishop.data.models.dto.OutputDto
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.common.Result
import io.jamshid.unishop.data.models.dto.ProductByOutput
import retrofit2.http.*

// Created by Usmon Abdurakhmanv on 5/13/2022.

interface SaleApi {

    @GET("output/all")
    suspend fun getAllSales(): List<OutputSales>

    @POST("output/create")
    suspend fun addOutput(@Body outputDto: OutputDto): Result<OutputSales>

    @GET("output/{id}/products")
    suspend fun getProductsByOutPut(@Path("id") id:Long):List<ProductByOutput>

    @GET("output/byDate")
    suspend fun getSalesByDate(
        @Query("from") from:Long,
        @Query("to") to:Long
    ):List<OutputSales>

    @GET("payment/sumMonths")
    suspend fun getLastSevenMonth():List<Double?>

}