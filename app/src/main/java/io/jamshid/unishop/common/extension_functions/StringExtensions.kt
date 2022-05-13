package io.jamshid.unishop.common.extension_functions

// Created by Usmon Abdurakhmanv on 5/13/2022.

fun String.getOnlyDigits(): String {
    val builder = StringBuilder()
    for (i in this) {
        if (i == '.') break

        if (i.isDigit()) {
            builder.append(i)
        }
    }
    return builder.toString()
}