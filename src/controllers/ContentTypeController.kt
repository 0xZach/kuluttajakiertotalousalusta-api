package com.turku.controllers

import com.turku.common.ApplicationLogger
import com.turku.common.respondError
import com.turku.common.respondSomethingWentWrong
import com.turku.common.respondSuccess
import com.turku.repositories.ContentTypeRepo
import io.ktor.server.application.*

suspend fun getContentTypes(call: ApplicationCall) {
    try {
        val contentTypes = ContentTypeRepo.getAll()
        if (contentTypes.isEmpty()) return call.respondError(422, "SKILL_LEVELS_NOT_FOUND")
        call.respondSuccess(mapOf("contentTypes" to contentTypes), "TYPES")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to fetch content types", error.printStackTrace())
        call.respondSomethingWentWrong()
    }
}