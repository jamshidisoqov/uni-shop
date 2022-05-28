package io.jamshid.unishop.data.remote.apis

import io.jamshid.unishop.common.Result
import io.jamshid.unishop.data.models.OutputPayment
import io.jamshid.unishop.data.models.dto.OutputProduct
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.data.models.dto.PaymentHistory
import io.jamshid.unishop.domain.models.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Created by Jamshid Isoqov an 5/21/2022
interface DebtApi {

    @GET("debt/all")
    suspend fun getAllDebt(): List<OutputSales>

    @GET("output/{id}/products")
    suspend fun getAllProductByOutput(@Path("id") id: Long): List<OutputProduct>

    @GET("output/{id}/payments")
    suspend fun getAllPaymentsBYOutput(@Path("id") id: Long): List<PaymentHistory>

    @POST("debt/pay")
    suspend fun newPayment(@Body outputPayment: OutputPayment): Result<OutputSales>

}