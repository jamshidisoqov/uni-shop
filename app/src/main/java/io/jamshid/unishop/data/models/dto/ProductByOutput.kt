package io.jamshid.unishop.data.models.dto

// Created by Jamshid Isoqov an 5/21/2022
data class ProductByOutput(
    var id:Long,
    var product:List<ProductDto>,
    var output:OutputSales,
    var quantity:Int,
    var cost:Double,
    var profitCost:Double
)
