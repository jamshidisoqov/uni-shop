package io.jamshid.unishop.common

sealed class Response<T>(var isLoading: Boolean = false, var data: T? = null, var message: String? = null) {
    class Success<T>(data: T?) : Response<T>(data = data!!)
    class Error<T>(message: String?) : Response<T>(message = message)
    class Loading<T> : Response<T>(isLoading = true)
    class Default<T> : Response<T>()
}
