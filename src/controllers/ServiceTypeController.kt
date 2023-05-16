package com.turku.controllers

import com.turku.common.ApplicationLogger
import com.turku.common.respondError
import com.turku.common.respondSomethingWentWrong
import com.turku.common.respondSuccess
import com.turku.repositories.ServiceTypeRepo
import io.ktor.server.application.*

suspend fun getServiceTypes(call: ApplicationCall) {
    try {
        val categories = ServiceTypeRepo.getAllByLocale(call.request.headers["Accept-Language"]!!)
        if (categories.isEmpty()) return call.respondError(422, "SERVICE_TYPES_NOT_FOUND")
        call.respondSuccess(categories, "SERVICE_TYPES")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to fetch service types", error.printStackTrace())
        call.respondSomethingWentWrong()
    }
}