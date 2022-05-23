package io.jamshid.unishop.presentation.feature_main.feature_debt.util

import io.jamshid.unishop.data.models.dto.Output

// Created by Jamshid Isoqov an 5/22/2022
interface DebtClickListener {
    fun onClick(outputSales: Output)
}