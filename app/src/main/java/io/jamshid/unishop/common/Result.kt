package io.jamshid.unishop.common

// Created by Usmon Abdurakhmanov on 5/13/2022.

data class Result<T>(var message: String, var active: Boolean, val status: Int, var body: T)