package io.jamshid.unishop.presentation.feature_main.feature_debt.util

import io.jamshid.unishop.data.models.dto.OutputSales

// Created by Jamshid Isoqov an 5/22/2022
interface DebtClickListener {
    fun onClick(outputSales: OutputSales)
}