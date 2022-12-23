package com.turku.common

import com.github.aymanizz.ktori18n.R
import com.github.aymanizz.ktori18n.t
import io.ktor.server.application.*
import io.ktor.server.response.*

fun <T> getResponse(code: Int, data: T, message: String): Map<String, Any?> {
    val response = mapOf(
        "code" to code,
        "data" to data,
        "message" to message,
    )
    if (code in 200..299) {
        return mapOf(
            "success" to response
        )
    }

    return mapOf(
        "error" to response
    )
}

suspend fun <T> ApplicationCall.respondSuccess(code: Int, data: T, message: String) {
    this.respond(getResponse(code, data, message))
}

suspend fun <T> ApplicationCall.respondSuccess(data: T, message: String) {
    this.respond(getResponse(200, data, message))
}

suspend fun ApplicationCall.respondSuccess(message: String) {
    this.respond(getResponse(200, emptyList<String>(), this.t(R(message))))
}


suspend fun ApplicationCall.respondError(code: Int, message: String) {
    this.respond(getResponse(code, emptyArray<String>(), this.t(R(message))))
}

suspend fun ApplicationCall.respondSomethingWentWrong() {
    this.respond(getResponse(422, emptyArray<String>(), "RESPONSE.DEFAULT.ERROR"))
}


suspend fun ApplicationCall.respondUnAuthorized() {
    this.respond(getResponse(401, emptyArray<String>(), "RESPONSE.DEFAULT.UN_AUTHORIZED"))
}
