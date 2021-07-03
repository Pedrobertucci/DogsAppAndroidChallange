package com.sword.health.remote

data class SafeRequest<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): SafeRequest<T> {
            return SafeRequest(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): SafeRequest<T> {
            return SafeRequest(Status.ERROR, data, msg)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR
}