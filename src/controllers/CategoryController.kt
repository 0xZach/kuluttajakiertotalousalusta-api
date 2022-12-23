package com.turku.controllers

import com.turku.common.ApplicationLogger
import com.turku.common.respondError
import com.turku.common.respondSomethingWentWrong
import com.turku.common.respondSuccess
import com.turku.repositories.CategoryRepo
import io.ktor.server.application.*

suspend fun getCategories(call: ApplicationCall) {
    try {
        val categories = CategoryRepo.getAll()
        if (categories.isEmpty()) return call.respondError(422, "CATEGORIES_NOT_FOUND")
        call.respondSuccess(categories, "CATEGORIES")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to fetch categories", error.printStackTrace())
        call.respondSomethingWentWrong()
    }
}
