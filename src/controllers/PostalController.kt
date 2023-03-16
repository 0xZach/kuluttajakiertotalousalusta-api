package com.turku.controllers

import com.turku.common.ApplicationLogger
import com.turku.common.respondError
import com.turku.common.respondSomethingWentWrong
import com.turku.common.respondSuccess
import com.turku.repositories.PostalRepo
import io.ktor.server.application.*

suspend fun getPostals(call: ApplicationCall) {
    try {

        val postals = PostalRepo.getAll()

        if (postals.isEmpty()) return call.respondError(422, "POSTAL_INFOS_NOT_FOUND")

        call.respondSuccess(
            mapOf("postals" to postals),
            "ALL")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to fetch postals", error.printStackTrace())
        call.respondSomethingWentWrong()
    }
}

suspend fun getPostalsByAddress(call: ApplicationCall) {

    try {
        val postalCode = call.parameters["postalCode"]!!

        val streetName = call.parameters["streetName"]!!

        var postals = PostalRepo.getByAddress(postalCode,streetName)

        if (postals.isEmpty()) return call.respondError(422, "POSTAL_INFOS_NOT_FOUND")
        call.respondSuccess(
            mapOf("postals" to postals),
            "POSTAL")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to fetch postals", error.printStackTrace())
        call.respondSomethingWentWrong()
    }

}