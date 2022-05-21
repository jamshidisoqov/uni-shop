package io.jamshid.unishop.data.models.dto


// Created by Usmon Abdurakhmanv on 5/14/2022.

data class Output(
    val id: Long? = null,
    val client: Client? = null,
    val products: Set<OutProductDto>? = null,

    )