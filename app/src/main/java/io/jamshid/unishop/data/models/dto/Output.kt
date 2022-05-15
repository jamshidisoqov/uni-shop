package io.jamshid.unishop.data.models.dto

import io.jamshid.unishop.domain.models.Client
import io.jamshid.unishop.domain.models.Product

// Created by Usmon Abdurakhmanv on 5/14/2022.

data class Output(
    val id: Long? = null,
    val client: Client? = null,
    val products: Set<OutProductDto>? = null,

    )